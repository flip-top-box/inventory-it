package com.it_inventory.api.Asset;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class AssetService {

    private final AssetRepository assetRepository;

    
    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public Asset addAsset(@RequestBody Asset asset) {
        asset.setDateOfCheckout(LocalDateTime.now());
        return assetRepository.save(asset);
    }
    
    public List<Asset> getAsset() {
        List<Asset> assets = assetRepository.findAll();
        System.out.println("Number of assets: " + assets.size());
        return assets;
    }
}

