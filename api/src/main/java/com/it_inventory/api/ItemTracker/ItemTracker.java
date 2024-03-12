package com.it_inventory.api.ItemTracker;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "item_tracking")
public class ItemTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackingId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "amount_added")
    private Integer amountAdded;

    @Column(name = "amount_removed")
    private Integer amountRemoved;

    @Temporal(TemporalType.DATE)
    @Column(name = "month_year")
    private Date monthYear;


    public Long getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(Long trackingId) {
        this.trackingId = trackingId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getAmountAdded() {
        return amountAdded;
    }

    public void setAmountAdded(Integer amountAdded) {
        this.amountAdded = amountAdded;
    }

    public Integer getAmountRemoved() {
        return amountRemoved;
    }

    public void setAmountRemoved(Integer amountRemoved) {
        this.amountRemoved = amountRemoved;
    }

    public Date getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(Date monthYear) {
        this.monthYear = monthYear;
    }
}
