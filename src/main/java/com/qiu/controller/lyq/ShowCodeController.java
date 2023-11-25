package com.qiu.controller.lyq;

import com.qiu.Rook.MapGetter;
import com.qiu.Rook.MapData;
import com.qiu.pojo.*;
import com.qiu.service.CodeInfoService;
import com.qiu.service.UnifiedCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import java.io.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class ShowCodeController {

    @Autowired
    @Qualifier("UnifiedCodeServiceImpl")
    private UnifiedCodeService unifiedCodeService;

    @Autowired
    @Qualifier("CodeInfoServiceImpl")
    private CodeInfoService codeInfoService;

    @PostMapping("/test")
    public String postTest(Model model,@RequestParam("Code") String Code,@RequestParam("Describe") String Describe
    ,@RequestParam("imageFiles") List<MultipartFile> imageFiles) {
        //错误判断，请补充：

        //插入文本编码
        unified_code code = new unified_code();
        code.setGeoCode(Code.substring(0,12)); //12位地理码
        code.setTimeCode(Code.substring(12,26)); //14位时间码
        code.setSourceCode(Code.substring(26,29));//3位来源码
        code.setCarrierCode(Code.substring(29,30));//1位载体码
        code.setDisasterCode(Code.substring(30,36));//6位指标码
        code.setDescription(Describe);//描述
        codeInfoService.insertCode(code); //插入文本编码

        //插入图片
        // Process and store the image files in the database
        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                byte[] imageData = new byte[0];
                try {
                    imageData = file.getBytes();
                    codeInfoService.insertPic(code,imageData); //插入图片
                    System.out.println(imageData);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //添加返回信息，请补充
        return "test/test_css";
    }

    @GetMapping("/test")
    public String showTest(Model model) {
        return "test/test_css";
    }

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


    public static String convertTimeCodeToChineseDescription(String timeCode) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = inputFormat.parse(timeCode);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "无效的时间码";
        }
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
        model.addAttribute("Time",convertTimeCodeToChineseDescription(code.getTimeCode()));
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
        model.addAttribute("base64ImageList", base64ImageList);

        return "imagePage";
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
        model.addAttribute("Time",convertTimeCodeToChineseDescription(code.getTimeCode()));

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

}
