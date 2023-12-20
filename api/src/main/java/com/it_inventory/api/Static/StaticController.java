package com.it_inventory.api.Static;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticController {

    @RequestMapping("/")
    public String index() {
        return "index"; // This refers to 'index.html' in 'src/main/resources/templates'
    }

    @RequestMapping("/asset-list")
    public String assetList() {
        return "asset-list"; // This refers to 'asset-list' in src/main/resources/templates
    }

    @RequestMapping("/inventory-list")
    public String inventoryList() {
        return "inventory-list";
    }
}
