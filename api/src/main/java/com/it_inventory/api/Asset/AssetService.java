package com.it_inventory.api.Asset;

import java.util.List;
import java.util.Optional;
import java.util.Map;


public interface AssetService {
    List<Asset> getAssets();
    Optional<Asset> getAssetById(Integer id);

    void updateAsset(Integer id, Map<String, Object> updates);

    Asset addAsset(Asset asset);

    boolean deleteAssetById(Integer id);
    List<Asset> findAssetsByDescription(String description);

    List<Asset> findAssetsByAssignedTo(String assigned_to);

    List<Asset> findAssetsCreatedBy(String created_by);

    List<Asset> getAssetBySerial(String serial);

    List<Asset> getAssetsByStatus(String status);

    List<Asset> findAssetsByLocation(String location);

    Map<String, Object> getAssetData(Integer id);

    void updateAssetDescription(Integer id, String new_description);

    void updateAssetSerial(Integer id, String new_serial);

    void updateAssetLocation(Integer id, String new_location);

    void updateAssetAssignedTo(Integer id, String new_location);

    void updateAssetStatus(Integer id, String new_location);
}



