package com.it_inventory.api.Static;

import com.it_inventory.api.Asset.AssetService;
import com.it_inventory.api.Asset.Asset; // Import the Asset class if it's in a different package
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List; // Added import for List

@Controller
@RequestMapping("/api/v1/static/assets")
public class StaticAssetController {

    private final AssetService assetService; // Ensure this service is injected or declared

    // Constructor for AssetService
    public StaticAssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public String getAssets(Model model) {
        try {
            List<Asset> assets = assetService.getAssets();
            model.addAttribute("assets", assets);
            return "assets_fragment"; // refers to 'assets_fragment.html'
        } catch (Exception e) {
            // logger.error("Error fetching assets", e);
            return "error_fragment"; // an error fragment view
        }
    }


}