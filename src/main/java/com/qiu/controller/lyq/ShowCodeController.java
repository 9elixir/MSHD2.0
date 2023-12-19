package com.qiu.controller.lyq;

import com.opencsv.CSVWriter;
import com.qiu.Rook.CityMap;
import com.qiu.Rook.MapGetter;
import com.qiu.Rook.MapData;
import com.qiu.pojo.*;
import com.qiu.service.CodeInfoService;
import com.qiu.service.GeoCodeInfoService;
import com.qiu.service.TimeService;
import com.qiu.service.UnifiedCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.util.*;

import java.io.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;



@Controller
public class ShowCodeController {

    @Autowired
    @Qualifier("UnifiedCodeServiceImpl")
    private UnifiedCodeService unifiedCodeService;

    @Autowired
    @Qualifier("CodeInfoServiceImpl")
    private CodeInfoService codeInfoService;

    @Autowired
    @Qualifier("TimeServiceImpl")
    private TimeService timeService;

    @Autowired
    @Qualifier("GeoCodeInfoServiceImpl")
    private GeoCodeInfoService geoCodeInfoService;

    @GetMapping("/showCodes")
    public String showCodes(Model model) {
        // Retrieve data from the Unified Codes service
        List<unified_code> unifiedCodes = unifiedCodeService.getAllUnifiedCodes();

        model.addAttribute("unifiedCodes", unifiedCodes);

        // Return the name of the JSP page to render
        return "showCode";
    }
    @PostMapping("/showCodes")
    public String queryCodes(Model model, @RequestParam("Code") String queryCode) {
        // Handle the query here
        List<unified_code> queryResult = unifiedCodeService.queryUnifiedCodes(queryCode);

        model.addAttribute("unifiedCodes", queryResult);

        return "showCode";
    }
    @GetMapping("/DropCode/{id}")
    public String DropCodeById(@PathVariable Integer id, Model model){
        // 使用 codeService 或其他方式获取指定 id 的 Code 对象
        unified_code code = unifiedCodeService.selectByPrimaryKey(id);
        // 删除图片与关系
        List<unified_code_Image_Relation> ImageRelations = codeInfoService.getAllImageRelationByUnifiedCode(code);
        for (unified_code_Image_Relation relation : ImageRelations){
            codeInfoService.DropImageRelation(relation);
        }
        // 删除视频与关系
        List<unified_code_Video_Relation> VideoRelations =codeInfoService.getAllVideoRelationByUnifiedCode(code);
        for (unified_code_Video_Relation relation : VideoRelations) {
            codeInfoService.DropVideoRelation(relation);
        }
        unifiedCodeService.deleteByPrimaryKey(code.getCodingId());
        return "redirect:/showCodes";
    }
    @GetMapping("/code/{id}")
    public String getCodeById(@PathVariable Integer id, Model model) {
        // 使用 codeService 或其他方式获取指定 id 的 Code 对象
        unified_code code = unifiedCodeService.selectByPrimaryKey(id);
        carrier_code_info CarrierCode = codeInfoService.selectCarrierCodeByUnifiedCode(code);
        disaster_code_info DisasterCode = codeInfoService.selectDisasterCodeByUnifiedCode(code);
        geo_code_info GeoCode = codeInfoService.selectGeoCodeByUnifiedCode(code);
        source_code_info SourceCode = codeInfoService.selectSourceCodeByUnifiedCode(code);

        // 将获取的 Code 对象传递给 JSP 页面
        model.addAttribute("code", code);
        model.addAttribute("CarrierCode",CarrierCode);
        model.addAttribute("DisasterCode",DisasterCode);
        model.addAttribute("GeoCode",GeoCode);
        model.addAttribute("Time",timeService.convertTimeCodeToChineseDescription(code.getTimeCode()));
        model.addAttribute("SourceCode",SourceCode);

        //以下为地图部分
        MapGetter mapGetter=new MapGetter();
        MapData mapdata=mapGetter.getBaiduMap(GeoCode.getProvince()+GeoCode.getCity()+GeoCode.getCounty()+GeoCode.getTown()+GeoCode.getVillage());
        model.addAttribute("Longtitude",mapdata.Longtitude);
        model.addAttribute("Latitude",mapdata.Latitude);
        // 返回显示 Code 信息的 JSP 页面
        return "codeDetails"; // 这里的 "codeDetails" 是你要展示信息的 JSP 页面
    }
    @RequestMapping("/displayImage/{id}")
    public String displayImage(@PathVariable Integer id,Model model) throws IOException {
        //获取对应id的code
        unified_code code = unifiedCodeService.selectByPrimaryKey(id);
        //获取所有关系，每个关系内部有一张图片
        List<unified_code_Image_Relation> Relations = codeInfoService.getAllImageRelationByUnifiedCode(code);
        //循环得到每一张图片
        List<String> base64ImageList = new ArrayList<>();
        for (unified_code_Image_Relation relation:Relations){
            //获取图片流
            byte[] imageBytes = codeInfoService.selectImageByImageRelation(relation);
            // 对图片进行Base64编码
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            // 写入到List里面
            base64ImageList.add(base64Image);
        }

        // 将Base64编码后的图片数据存储到模型
        model.addAttribute("ImageRelation",Relations);
        model.addAttribute("base64ImageList", base64ImageList);

        return "imagePage";
    }
    @RequestMapping("/displayVideo/{id}")
    public String showVideo(@PathVariable Integer id, Model model) {
        //获取对应id的code
        unified_code code = unifiedCodeService.selectByPrimaryKey(id);
        //获取所有关系，每个关系内部有一个视频
        List<unified_code_Video_Relation> Relations = codeInfoService.getAllVideoRelationByUnifiedCode(code);
        //循环得到每一个视频
        List<String> base64List = new ArrayList<>();
        for (unified_code_Video_Relation relation:Relations){
            //获取图片流
            byte[] Bytes = codeInfoService.selectVideoByVideoRelation(relation);
            // 对图片进行Base64编码
            String base64String = Base64.getEncoder().encodeToString(Bytes);
            // 写入到List里面
            base64List.add(base64String);
            System.out.println(base64String);
        }
        model.addAttribute("VideoRelations",Relations);
        model.addAttribute("videoData", base64List);
        return "videoPage"; // 这里假设你的 JSP 页面名称为 videoPage.jsp
    }
    @GetMapping("/polymerCode/{id}")
    public String getAuxiliaryCodeById(@PathVariable Integer id, Model model) {
        // 使用 codeService 或其他方式获取指定 id 的 Code 对象
        unified_code code = unifiedCodeService.selectByPrimaryKey(id);
        carrier_code_info CarrierCode = codeInfoService.selectCarrierCodeByUnifiedCode(code);
        disaster_code_info DisasterCode = codeInfoService.selectDisasterCodeByUnifiedCode(code);
        geo_code_info GeoCode = codeInfoService.selectGeoCodeByUnifiedCode(code);
        source_code_info SourceCode = codeInfoService.selectSourceCodeByUnifiedCode(code);

        //添加主码的code、geo、time
        model.addAttribute("code_2",code);//来不及修改了，应该换一种形式编写;
        model.addAttribute("code",code.getGeoCode()+code.getTimeCode()+code.getSourceCode()+code.getCarrierCode()+code.getDisasterCode());
        model.addAttribute("geo",GeoCode.getProvince()+GeoCode.getCity()+GeoCode.getCounty()+GeoCode.getTown()+GeoCode.getVillage());
        model.addAttribute("Time",timeService.convertTimeCodeToChineseDescription(code.getTimeCode()));

        //查询辅助码
        List<unified_code> auxiliaryCodeList =codeInfoService.getAuxiliaryCodeListByMainCode(code);
        model.addAttribute("auxiliaryCodeList",auxiliaryCodeList);
        List<String> SourceInfoList = new ArrayList<>();
        List<String> CarrierInfoList = new ArrayList<>();
        List<String> DisasterInfoList = new ArrayList<>();
        for (unified_code auxiliaryCode : auxiliaryCodeList)
        {
            source_code_info SourceInfo = codeInfoService.selectSourceCodeByUnifiedCode(auxiliaryCode);
            carrier_code_info CarrierInfo = codeInfoService.selectCarrierCodeByUnifiedCode(auxiliaryCode);
            disaster_code_info DisaterInfo = codeInfoService.selectDisasterCodeByUnifiedCode(auxiliaryCode);

            //来自:${SourceCode.getSubCategory()}，属于${SourceCode.getMainCategory()}
            SourceInfoList.add("来自:"+SourceInfo.getMainCategory()+",属于"+SourceInfo.getMainCategory());
            CarrierInfoList.add(CarrierInfo.getCarrierForm());
            //${DisasterCode.getSubCategory()}:${DisasterCode.getDisasterIndicators()}的情况是${code.getDescription()}
            DisasterInfoList.add(DisaterInfo.getSubCategory()+DisaterInfo.getDisasterIndicators()+"的情况是"+auxiliaryCode.getDescription());
        }
        model.addAttribute("SourceInfoList",SourceInfoList);
        model.addAttribute("CarrierInfoList",CarrierInfoList);
        model.addAttribute("DisasterInfoList",DisasterInfoList);

        //以下为地图部分
        MapGetter mapGetter=new MapGetter();
        MapData mapdata=mapGetter.getBaiduMap(GeoCode.getProvince()+GeoCode.getCity()+GeoCode.getCounty()+GeoCode.getTown()+GeoCode.getVillage());
        model.addAttribute("Longtitude",mapdata.Longtitude);
        model.addAttribute("Latitude",mapdata.Latitude);

        return "polymerCode";
    }

    @GetMapping("/upload")
    public String showTest(Model model) {

        return "uploadSite";
    }

    @PostMapping("/upload")
    public String postTest(Model model,@RequestParam("Code") String Code,@RequestParam("Describe") String Describe
            ,@RequestParam("imageFiles") List<MultipartFile> imageFiles) {
        //错误判断，请补充：
        if (Code.length() < 36)
            return "uploadSite";
        //插入文本编码
        unified_code code = new unified_code();
        code.setGeoCode(Code.substring(0,12)); //12位地理码
        code.setTimeCode(Code.substring(12,26)); //14位时间码
        code.setSourceCode(Code.substring(26,29));//3位来源码
        code.setCarrierCode(Code.substring(29,30));//1位载体码
        code.setDisasterCode(Code.substring(30,36));//6位指标码
        code.setDescription(Describe);//描述
        codeInfoService.insertCode(code); //插入文本编码

        //插入文件
        // Process and store the image files in the database
        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                try {
                    byte[] imageData = file.getBytes();
                    //System.out.println(imageData);
                    System.out.println(code.getCarrierCode());
                    if (Objects.equals(code.getCarrierCode(), "2")) //图片
                        codeInfoService.insertPic(code,imageData); //插入图片
                    if (Objects.equals(code.getCarrierCode(), "3")) //音频
                        codeInfoService.insertAudio(code,imageData); //插入音频
                    if (Objects.equals(code.getCarrierCode(), "4")) {//视频
                        codeInfoService.insertVideo(code, imageData);//插入视频
                        System.out.println("Video");
                    }
                    //System.out.println(imageData);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //添加返回信息，请补充
        return "uploadSite";
    }



    @GetMapping("/code_edit")
    public String showCodeEditPage() {
        return "codeEdit"; // 返回编辑XML页面的视图名
    }

    @PostMapping("/code_edit")
    public String postTest(Model model, @RequestParam("xmlData") String xmlData) {
        try {
            // 创建一个DOM解析器工厂
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // 创建DOM解析器
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 将XML数据作为输入源进行解析
            InputSource inputSource = new InputSource(new StringReader(xmlData));

            // 解析XML数据，获得Document对象
            Document document = builder.parse(inputSource);

            // 获取根元素
            Element rootElement = document.getDocumentElement();

            // 获取Code元素的值
            String codeValue = getElementValue(rootElement, "Code");
            System.out.println("Code value: " + codeValue);
            // 获取Description元素的值
            String descriptionValue = getElementValue(rootElement, "Description");

            // 错误判断，请补充
            if (codeValue == null || codeValue.length() < 36) {
                return "codeEdit";
            }

            // 插入文本编码
            unified_code code = new unified_code();
            code.setGeoCode(codeValue.substring(0, 12)); // 12位地理码
            code.setTimeCode(codeValue.substring(12, 26)); // 14位时间码
            code.setSourceCode(codeValue.substring(26, 29)); // 3位来源码
            code.setCarrierCode(codeValue.substring(29, 30)); // 1位载体码
            code.setDisasterCode(codeValue.substring(30, 36)); // 6位指标码
            code.setDescription(descriptionValue); // 描述
            codeInfoService.insertCode(code); // 插入文本编码

            // 解析图片、音频和视频
            NodeList imageNodes = rootElement.getElementsByTagName("ImageFile");
            if (imageNodes != null && imageNodes.getLength() > 0) {
                for (int i = 0; i < imageNodes.getLength(); i++) {
                    Element imageElement = (Element) imageNodes.item(i);
                    String imageBase64 = imageElement.getTextContent();
                    byte[] imageData = Base64.getDecoder().decode(imageBase64);
                    codeInfoService.insertPic(code, imageData); // 插入图片
                }
            }

            NodeList audioNodes = rootElement.getElementsByTagName("AudioFile");
            if (audioNodes != null && audioNodes.getLength() > 0) {
                for (int i = 0; i < audioNodes.getLength(); i++) {
                    Element audioElement = (Element) audioNodes.item(i);
                    String audioBase64 = audioElement.getTextContent();
                    byte[] audioData = Base64.getDecoder().decode(audioBase64);
                    codeInfoService.insertAudio(code, audioData); // 插入音频
                }
            }

            NodeList videoNodes = rootElement.getElementsByTagName("VideoFile");
            if (videoNodes != null && videoNodes.getLength() > 0) {
                for (int i = 0; i < videoNodes.getLength(); i++) {
                    Element videoElement = (Element) videoNodes.item(i);
                    String videoBase64 = videoElement.getTextContent();
                    byte[] videoData = Base64.getDecoder().decode(videoBase64);
                    codeInfoService.insertVideo(code, videoData); // 插入视频
                }
            }

            // 添加返回信息，请补充
            model.addAttribute("message", "XML解析成功并上传");
            return "codeEdit";
        } catch (Exception e) {
            // 处理解析错误
            e.printStackTrace();
            return "codeEdit";
        }
    }

    // 从指定的元素中获取子元素的值
    private String getElementValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            Element subElement = (Element) nodeList.item(0);
            return subElement.getTextContent();
        }
        return null;
    }


    @GetMapping("/exportCSV")
    public void exportCSV(HttpServletResponse response) throws IOException {
        //获取数据
        List<unified_code> unifiedCodes = unifiedCodeService.getAllUnifiedCodes();

        //转换为CSV格式
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter=new CSVWriter(writer);

        //添加标题行
        csvWriter.writeNext(new String[]{"Geo Code","Time Code","Source Code","Carrier Code","Disaster Code","Description"});
        for (unified_code code:unifiedCodes) {
            csvWriter.writeNext(new String[]{code.getGeoCode(),code.getTimeCode(),code.getSourceCode(),code.getCarrierCode(),code.getDisasterCode(),code.getDescription()});
        }

        //创建文件并写入CSV数据
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=exportedData.csv");

        //提供下载链接
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(writer.toString().getBytes());
        outputStream.flush();
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> downloadImage(@RequestParam("ImageId") Integer ImageId,@RequestParam("CodingId") Integer CodingId) {
        unified_code_Image_Relation imgRelation = new unified_code_Image_Relation();
        imgRelation.setCodingId(CodingId);
        imgRelation.setImageId(ImageId);
        byte[] imageBytes = codeInfoService.selectImageByImageRelation(imgRelation);

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytes.length);
        headers.setContentDispositionFormData("attachment", "image.jpg");

        // Return the response entity with image bytes and headers
        return ResponseEntity.ok().headers(headers).body(imageBytes);
    }

    @GetMapping("/video")
    public  ResponseEntity<byte[]> downloadVideo (@RequestParam("VideoId") Integer VideoId,@RequestParam("CodingId") Integer CodingId) {
        unified_code_Video_Relation VideoRelation = new unified_code_Video_Relation();
        VideoRelation.setVideoId(VideoId);
        VideoRelation.setCodingId(CodingId);
        byte[] videoBytes = codeInfoService.selectVideoByVideoRelation(VideoRelation);

        // Set response headers for video
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(videoBytes.length);
        headers.setContentDispositionFormData("attachment", "video.mp4");

        // Return the response entity with video bytes and headers
        return ResponseEntity.ok().headers(headers).body(videoBytes);
    }

    public static String convertImageToBase64(BufferedImage image) {
        String base64String = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bos);
            byte[] imageBytes = bos.toByteArray();
            base64String = Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64String;
    }


    @GetMapping("/lineChart/{i}")
    public String generateLineChart(Model model,@PathVariable Integer i,@RequestParam("year") Integer year) {
        geoCodeInfoService.init();
        //目标的年份
        model.addAttribute("i",i);
        model.addAttribute("year",year);
        String cur = year.toString()+"1220000000";
        String city = geoCodeInfoService.getCity(i);
        model.addAttribute("city",city);
        System.out.println(city);
        geo_code_info firstCode = geoCodeInfoService.getFirstGeoCode(i);
        geo_code_info lastCode = geoCodeInfoService.getLastGeoCode(i);
        // Create dataset
        XYSeries series = new XYSeries("Data");
        int total=0;
        for (int month = 12;month >= 1;month --) {
            List<unified_code> list = codeInfoService.getCodeListByCityAndTime(firstCode.getGeoCode(),lastCode.getGeoCode(),
                    timeService.getCurrentMonth(cur),timeService.getNextMonth(cur));
            cur = timeService.getPreviousMonth(cur);
            series.add(month, list.size());
            total+=list.size();
        }
        model.addAttribute("total",total);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "The disaster situation in the local area in the past year",
                "Months",
                "Disaster situation",
                dataset
        );

        // Convert chart to BufferedImage
        BufferedImage bufferedImage = chart.createBufferedImage(600, 400);

        // Convert BufferedImage to Base64 string
        String base64LineChart = convertImageToBase64(bufferedImage);

        // Add Base64 string to model
        model.addAttribute("lineChartBase64", base64LineChart);
        return "lineChart";
    }

    @GetMapping("/testMap")
    public String testMap(Model model){
        return "map";
    }
}
