package com.example.demo.model;

public class Market{
    public long id;
    public String equity;
    public long price;

    // NEW: sector info for API
    public long sectorId;
    public String sectorName;

    public Market() {
    }

    public Market(long id, String equity, long price, long sectorId, String sectorName) {
        this.id = id;
        this.equity = equity;
        this.price = price;
        this.sectorId = sectorId;
        this.sectorName = sectorName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEquity() {
        return equity;
    }

    public void setEquity(String equity) {
        this.equity = equity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getSectorId() {
        return sectorId;
    }

    public void setSectorId(long sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }
}