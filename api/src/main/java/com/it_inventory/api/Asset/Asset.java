package com.it_inventory.api.Asset;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="asset")
public class Asset {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Asset Tag ID")
    private Integer assetID;

    @Column(name = "Description")
    private String asset;

    @Column(name = "Serial No")
    private String assetSerial;

    @Column(name = "Location")
    private String assetLocation;

    @Column(name = "Assigned to")
    private String assetEmpName;

    @Column(name = "Status")
    private String assetStatus;

    @Column(name = "Date Created")
    private LocalDateTime dateOfCheckout;
    
    @Column(name = "Created by")
    private String assetCreator;
    
    //getters and setters galore
    public Integer getAssetID() {
        return assetID;
    }

    public void setAssetID(Integer assetID) {
        this.assetID = assetID;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getAssetSerial() {
        return assetSerial;
    }

    public void setAssetSerial(String assetSerial) {
        this.assetSerial = assetSerial;
    }

    public String getAssetLocation() {
        return assetLocation;
    }

    public void setAssetLocation(String assetLocation) {
        this.assetLocation = assetLocation;
    }

    public String getAssetEmpName() {
        return assetEmpName;
    }

    public void setAssetEmpName(String assetEmpName) {
        this.assetEmpName = assetEmpName;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public LocalDateTime getDateOfCheckout() {
        return dateOfCheckout;
    }

    public void setDateOfCheckout(LocalDateTime dateOfCheckout) {
        this.dateOfCheckout = dateOfCheckout;
    }

    public String getAssetCreator() {
        return assetCreator;
    }

    public void setAssetCreator(String assetCreator) {
        this.assetCreator = assetCreator;
    }

    @Override
    public String toString() {
        return "Asset [assetID=" + assetID + ", ...]" + 
        ", asset=" + asset + 
        ", assetSerial=" + assetSerial +
        ", assetLocation=" + assetLocation + 
        ", assetEmpName=" + assetEmpName + 
        ", assetStatus=" + assetStatus +
        ", assetCreator=" + assetCreator +
        ", dateOfCheckout=" + dateOfCheckout + "]";
    }

    


}
