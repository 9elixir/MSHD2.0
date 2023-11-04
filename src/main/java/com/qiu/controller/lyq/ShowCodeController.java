package com.qiu.controller.lyq;

import com.qiu.pojo.*;
import com.qiu.service.CodeInfoService;
import com.qiu.service.UnifiedCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        model.addAttribute("abc",123);

        // Return the name of the JSP page to render
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

    @RequestMapping("/displayImage")
    public String displayImage(Model model) throws IOException {
        //获取id为8的code
        unified_code code = unifiedCodeService.selectByPrimaryKey(8);
        //计算有多少张图片
        int num = codeInfoService.countImageOfUnifiedCode(code);
        //获取所有关系
        List<unified_code_Image_Relation> Relations = codeInfoService.getAllImageRelationByUnifiedCode(code);
        //只有一张图片
        unified_code_Image_Relation relation = Relations.get(0);
        byte[] imageBytes = codeInfoService.selectImageByImageRelation(relation);

// 对图片进行Base64编码
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
// 将Base64编码后的图片数据存储到模型
        model.addAttribute("base64Image", base64Image);

        //如果有多张图片，应该遍历，然后提供一个List数组，在jsp里面应该循环显示这个list数组
        return "imagePage";
    }
}
