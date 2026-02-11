package com.fors.web;

import com.fors.service.ConfigurationTreeService;
import com.fors.service.DocumentDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ConfigurationTreeService configurationTreeService;
    private final DocumentDataService documentDataService;

    public AdminController(
            ConfigurationTreeService configurationTreeService,
            DocumentDataService documentDataService) {
        this.configurationTreeService = configurationTreeService;
        this.documentDataService = documentDataService;
    }

    @GetMapping
    public String adminHome(Model model) {
        List<ConfigurationTreeService.TenantNode> tenants = configurationTreeService.loadConfigurationTree();
        model.addAttribute("tenants", tenants);
        return "admin/config";
    }

    @GetMapping("/data")
    public String viewData(
            @RequestParam(defaultValue = "demo-tenant") String tenantId,
            Model model) {
        List<DocumentDataService.DocumentInstanceNode> documents = 
                documentDataService.loadDocumentInstances(tenantId);
        model.addAttribute("tenantId", tenantId);
        model.addAttribute("documents", documents);
        return "admin/data";
    }
}
