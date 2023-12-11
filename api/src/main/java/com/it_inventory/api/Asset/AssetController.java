package com.it_inventory.api.Asset;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/assets")
public class AssetController {

    private final AssetService assetService;
    
    
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public List<Asset> getAsset() {
        return assetService.getAsset();
    }

    @PostMapping("/add")
    public Asset addAsset(@RequestBody Asset asset) {
        return assetService.addAsset(asset);
    }
}
