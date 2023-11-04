package com.qiu.controller.lyq;

import com.qiu.pojo.unified_code;
import com.qiu.service.UnifiedCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShowCodeController {

    @Autowired
    @Qualifier("UnifiedCodeServiceImpl")
    private UnifiedCodeService unifiedCodeService;

    @GetMapping("/showCodes")
    public String showCodes(Model model) {
        // Retrieve data from the Unified Codes service
        List<unified_code> unifiedCodes = unifiedCodeService.getAllUnifiedCodes();
        model.addAttribute("unifiedCodes", unifiedCodes);

        System.out.println("123\n");
        // 使用增强的 for-each 循环
        for (unified_code code : unifiedCodes) {
            System.out.println("Code ID: " + code.getCodingId());
            System.out.println("Code Name: " + code.getDescription());
            // 继续输出其他属性
        }
        // Return the name of the JSP page to render
        return "showCode";
    }
}
