package com.it_inventory.api.Asset;

import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;


@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    //////////////////////////////////////////////////////////////////////
    /* !!! ADD METHODS !!! */
    /////////////////////////////////////////////////////////////////////
    @Override
    public Asset addAsset(Asset asset) {
        return assetRepository.save(asset);
    }
//////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////
    /* !!! GET METHODS !!! */
    /////////////////////////////////////////////////////////////////////
    @Override
    public List<Asset> getAssets() {
        return assetRepository.findAll();
    }

    @Override
    public Optional<Asset> getAssetById(Integer id) {
        return assetRepository.findById(id);
    }

    @Override
    public List<Asset> getAssetsByStatus(String status) {
        return assetRepository.getAssetsByStatus(status);
    }

    @Override
    public List<Asset> getAssetBySerial(String serial) {
        return assetRepository.getAssetBySerial(serial);
    }

//////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////
    /* !!! DELETE METHODS !!! */
    /////////////////////////////////////////////////////////////////////
    @Override
    public boolean deleteAssetById(Integer id) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);

        if (optionalAsset.isPresent()) {
            assetRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////
    /* !!! FIND-BY(PARAM) METHODS !!! */
    /////////////////////////////////////////////////////////////////////

    @Override
    public List<Asset> findAssetsByDescription(String description) {
        return assetRepository.findAssetsByDescription(description);
    }

    @Override
    public List<Asset> findAssetsByAssignedTo(String assigned_to) {
        return assetRepository.findAssetsByAssignedTo(assigned_to);
    }

    @Override
    public List<Asset> findAssetsCreatedBy(String created_by) {
        return assetRepository.findAssetsCreatedBy(created_by);
    }

    @Override
    public List<Asset> findAssetsByLocation(String location) {
        return assetRepository.findAssetsByLocation(location);
    }
///////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////
    /* !!! PUT METHODS !!! */
    /////////////////////////////////////////////////////////////////////

    // all in one to get it done
    @Override
    public void updateAsset(Integer id, Map<String, Object> updates) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);

        if (optionalAsset.isPresent()) {
            Asset asset = optionalAsset.get();

            // Update only the fields present in the JSON request
            if (updates.containsKey("description")) {
                asset.setDescription((String) updates.get("description"));
            }
            if (updates.containsKey("serial")) {
                asset.setSerial((String) updates.get("serial"));
            }
            if (updates.containsKey("location")) {
                asset.setLocation((String) updates.get("location"));
            }
            if (updates.containsKey("assignedTo")) {
                asset.setAssignedTo((String) updates.get("assignedTo"));
            }
            if (updates.containsKey("status")) {
                asset.setStatus((String) updates.get("status"));
            }

            assetRepository.save(asset);
        } else {
            throw new EntityNotFoundException("Asset not found with ID: " + id);
        }
    }

    //GET DATA FOR PUT METHODS
    @Override
    public Map<String, Object> getAssetData(Integer id) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);

        if (optionalAsset.isPresent()) {
            Asset asset = optionalAsset.get();

            Map<String, Object> assetData = new HashMap<>();
            assetData.put("id", asset.getId());
            assetData.put("description", asset.getDescription());
            assetData.put("serial", asset.getSerial());
            assetData.put("location", asset.getLocation());
            assetData.put("status", asset.getStatus());
            assetData.put("dateCreated", asset.getDateCreated());
            assetData.put("assignedTo", asset.getAssignedTo());
            assetData.put("createdBy", asset.getCreatedBy());

            return assetData;
        } else {
            throw new EntityNotFoundException("Asset not found with id: " + id);
        }
    }

    @Override
    public void updateAssetDescription(Integer id, String new_description) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);

        if (optionalAsset.isPresent()) {
            Asset asset = optionalAsset.get();
            asset.setDescription(new_description);
            assetRepository.save(asset);
        } else {
            throw new EntityNotFoundException("Asset not found with id: " + id);
        }
    }

    @Override
    public void updateAssetSerial(Integer id, String new_serial) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);

        if (optionalAsset.isPresent()) {
            Asset asset = optionalAsset.get();
            asset.setSerial(new_serial);
            assetRepository.save(asset);
        } else {
            throw new EntityNotFoundException("Asset not found with id: " + id);
        }
    }

    @Override
    public void updateAssetLocation(Integer id, String new_location) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);

        if (optionalAsset.isPresent()) {
            Asset asset = optionalAsset.get();
            asset.setLocation(new_location);
            assetRepository.save(asset);
        } else {
            throw new EntityNotFoundException("Asset not found with id: " + id);
        }
    }

    @Override
    public void updateAssetAssignedTo(Integer id, String new_assigned_to) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);

        if (optionalAsset.isPresent()) {
            Asset asset = optionalAsset.get();
            asset.setAssignedTo(new_assigned_to);
            assetRepository.save(asset);
        } else {
            throw new EntityNotFoundException("Asset not found with id: " + id);
        }
    }

    @Override
    public void updateAssetStatus(Integer id, String new_status) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);

        if (optionalAsset.isPresent()) {
            Asset asset = optionalAsset.get();
            asset.setStatus(new_status);
            assetRepository.save(asset);
        } else {
            throw new EntityNotFoundException("Asset not found with id: " + id);
        }
    }
///////////////////////////////////////////////////////////////////////////

}
