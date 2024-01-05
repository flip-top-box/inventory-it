package com.it_inventory.api.Asset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import jakarta.persistence.EntityNotFoundException;

import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@RestController
@RequestMapping(path = "api/v1/assets")
public class AssetController {

    private final AssetService assetService;
    private static final Logger logger = LoggerFactory.getLogger(AssetController.class);


    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }


//////////////////////////////////////////////////////////
    /* !!!GET ROUTES!!! */                             //
/////////////////////////////////////////////////////////

    //Route to GET ALL assets
    @GetMapping
    public ResponseEntity<List<Asset>> getAssets() {
        try {
            List<Asset> assets = assetService.getAssets();
            return ResponseEntity.ok(assets);
        } catch (Exception e) {
            logger.error("Error fetching assets", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("get_by_id/{id}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Integer id) {
        try {
            Optional<Asset> asset = assetService.getAssetById(id);
            return asset.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get_by_description/{description}")
    public ResponseEntity<List<Asset>> getAssetsByDescription(@PathVariable String description) {
        try {
            List<Asset> assets = assetService.findAssetsByDescription(description);
            return ResponseEntity.ok(assets);
        } catch (Exception e) {
            // You can log the exception for debugging purposes
            logger.error("An error occurred while fetching assets by description", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    //Get asset by the employee is assigned to. Method compares the name entered to DB,
    // The name does not need to be an exact match and full name is not required
    @GetMapping("/get_by_assigned_to/{assigned_to}")
    public ResponseEntity<List<Asset>> getAssetsByAssignedTo(@PathVariable String assigned_to) {
        try {
            List<Asset> assets = assetService.findAssetsByAssignedTo(assigned_to);
            return ResponseEntity.ok(assets);
        } catch (Exception e) {
            logger.error("An error occurred while fetching assets by assigned_to", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get_created_by/{created_by}")
    public ResponseEntity<List<Asset>> getAssetsCreatedBy(@PathVariable String created_by) {
        try {
            List<Asset> assets = assetService.findAssetsCreatedBy(created_by);
            return ResponseEntity.ok(assets);
        } catch (Exception e) {
            logger.error("An error occurred while fetching assets created_by", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("get_by_serial/{serial}")
    public ResponseEntity<List<Asset>> getAssetBySerial(@PathVariable String serial) {
        try {
            List<Asset> asset = assetService.getAssetBySerial(serial);
            return ResponseEntity.ok(asset);
        } catch (Exception e) {
            logger.error("An error occurred while fetching asset serial", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get_by_location/{location}")
    public ResponseEntity<List<Asset>> getAssetsByLocation(@PathVariable String location) {
        try {
            List<Asset> assets = assetService.findAssetsByLocation(location);
            return ResponseEntity.ok(assets);
        } catch (Exception e) {
            // You can log the exception for debugging purposes
            logger.error("An error occurred while fetching assets by location", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get_by_status/{status}")
    public ResponseEntity<List<Asset>> getAssetsByStatus(@PathVariable String status) {
        try {
            List<Asset> assets = assetService.getAssetsByStatus(status);
            return ResponseEntity.ok(assets);
        } catch (Exception e) {
            // You can log the exception for debugging purposes
            logger.error("An error occurred while fetching assets by status", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

///////////////////////////////////////////////////////
    /* !!! ADD ROUTES !!! */                        //
//////////////////////////////////////////////////////

    //Route to ADD an asset
    @PostMapping("/add")
    public ResponseEntity<Asset> addAsset(@RequestBody Asset asset) {
        asset.setDateCreated(LocalDateTime.now());
        Asset savedAsset = assetService.addAsset(asset);
        return new ResponseEntity<>(savedAsset, HttpStatus.CREATED);
    }

///////////////////////////////////////////////////////
    /* !!! DELETE ROUTES !!! */                     //
//////////////////////////////////////////////////////

    //Route to DELETE asset record based on asset ID
    @DeleteMapping("/delete_by_id/{id}")
    public ResponseEntity<String> deleteAsset(@PathVariable Integer id) {
        boolean deleted = assetService.deleteAssetById(id);

        if (deleted) {
            return new ResponseEntity<>("Asset deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Asset not found", HttpStatus.NOT_FOUND);
        }
    }

//////////////////////////////////////////////////////////
    /* !!! PUT ROUTES !!! */                            //
//////////////////////////////////////////////////////////

    @PutMapping("/{id}/update_description")
    public ResponseEntity<Map<String, Object>> updateAssetDescription(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) {
        try {
            String newDescription = requestBody.get("new_description");
            assetService.updateAssetDescription(id, newDescription);

            // Retrieve and return asset data in the desired format
            Map<String, Object> assetData = assetService.getAssetData(id);
            return ResponseEntity.ok(assetData);
        } catch (EntityNotFoundException e) {
            logger.warn("Asset not found with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating asset description", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }

    @PutMapping("/{id}/update_serial")
    public ResponseEntity<Map<String, Object>> updateAssetSerial(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) {
        try {
            String newSerial = requestBody.get("new_serial");
            assetService.updateAssetSerial(id, newSerial);

            // Retrieve and return asset data in the desired format
            Map<String, Object> assetData = assetService.getAssetData(id);
            return ResponseEntity.ok(assetData);
        } catch (EntityNotFoundException e) {
            logger.warn("Asset not found with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating asset serial", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }

    @PutMapping("/{id}/update_location")
    public ResponseEntity<Map<String, Object>> updateAssetLocation(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) {
        try {
            String newLocation = requestBody.get("new_location");
            assetService.updateAssetLocation(id, newLocation);

            // Retrieve and return asset data in the desired format
            Map<String, Object> assetData = assetService.getAssetData(id);
            return ResponseEntity.ok(assetData);
        } catch (EntityNotFoundException e) {
            logger.warn("Asset not found with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating asset location", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }

    @PutMapping("/{id}/update_assigned_to")
    public ResponseEntity<Map<String, Object>> updateAssetAssignedTo(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) {
        try {
            String newAssignedTo = requestBody.get("new_assigned_to");
            assetService.updateAssetAssignedTo(id, newAssignedTo);

            // Retrieve and return asset data in the desired format
            Map<String, Object> assetData = assetService.getAssetData(id);
            return ResponseEntity.ok(assetData);
        } catch (EntityNotFoundException e) {
            logger.warn("Asset not found with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating asset location", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }

    @PutMapping("/{id}/update_status")
    public ResponseEntity<Map<String, Object>> updateAssetStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) {
        try {
            String newStatus = requestBody.get("new_status");
            assetService.updateAssetStatus(id, newStatus);

            // Retrieve and return asset data in the desired format
            Map<String, Object> assetData = assetService.getAssetData(id);
            return ResponseEntity.ok(assetData);
        } catch (EntityNotFoundException e) {
            logger.warn("Asset not found with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating asset location", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }
}



