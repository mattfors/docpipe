package com.fors.web;

import com.fors.service.ConfigurationTreeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ConfigurationTreeService configurationTreeService;

    public AdminController(ConfigurationTreeService configurationTreeService) {
        this.configurationTreeService = configurationTreeService;
    }

    @GetMapping
    public String adminHome(Model model) {
        List<ConfigurationTreeService.TenantNode> tenants = configurationTreeService.loadConfigurationTree();
        model.addAttribute("tenants", tenants);
        return "admin/config";
    }
}
