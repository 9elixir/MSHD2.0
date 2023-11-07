package com.qiu.controller.lyq;

import com.qiu.pojo.*;
import com.qiu.service.CodeInfoService;
import com.qiu.service.UnifiedCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
public class ShowCodeController {

    @Autowired
    @Qualifier("UnifiedCodeServiceImpl")
    private UnifiedCodeService unifiedCodeService;

    @Autowired
    @Qualifier("CodeInfoServiceImpl")
    private CodeInfoService codeInfoService;

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
}
