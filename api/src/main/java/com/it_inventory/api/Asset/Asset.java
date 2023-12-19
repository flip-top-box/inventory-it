package com.it_inventory.api.Asset;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="asset")
public class Asset {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String description;
    private String serial;
    private String location;
    private String assigned_to;
    private String status;
    private String created_by;
    private LocalDateTime date_created;
    
    //getters and setters galore
    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAssignedTo() {
        return assigned_to;
    }

    public void setAssignedTo(String assigned_to) {
        this.assigned_to = assigned_to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return date_created;
    }

    public void setDateCreated(LocalDateTime date_created) {
        this.date_created = date_created;
    }

    public String getCreatedBy() {
        return created_by;
    }

    public void setCreatedBy(String created_by) {
        this.created_by = created_by;
    }

    @Override
    public String toString() {
        return "Asset [id=" + id + ", ...]" +
        ", description=" + description +
        ", serial=" + serial +
        ", location=" + location +
        ", assigned_to=" + assigned_to +
        ", status=" + status +
        ", created_by=" + created_by +
        ", date_created=" + date_created + "]";
    }

}
