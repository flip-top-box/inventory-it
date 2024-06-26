package com.it_inventory.api.Item;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table(name="storage_room")
public class Item {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String description;
    private String brand;
    private String location;
    private String model;
    private Boolean send_email;
    private Integer min_val;
    private Integer count;

    private Boolean is_asset;

    private String type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getSend_email() {
        return send_email;
    }

    public void setSend_email(Boolean send_email) {
        this.send_email = send_email;
    }

    public Integer getMin_val() {
        return min_val;
    }

    public void setMin_val(Integer min_val) {
        this.min_val = min_val;
    }

    public Boolean getIs_asset() {
        return is_asset;
    }

    public void setIs_asset(Boolean is_asset) {
        this.is_asset = is_asset;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
