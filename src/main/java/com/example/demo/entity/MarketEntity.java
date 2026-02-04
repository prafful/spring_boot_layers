package com.example.demo.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="myMarket")
public class MarketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank(message = "equity is required")
    @Column(nullable = false)
    public String equity;

    @Column(nullable = false)
    public long price;

    // Many Market(equity) rows belong to ONE Sector row
    /*
    @ManyToOne = “many stocks point to one sector”
    sector_id column will appear in the market table
    optional=false + nullable=false means: a Market row must have a Sector
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "sector_id", nullable = false)
    private SectorEntity sector;

    public MarketEntity(Long id, String equity, long price, SectorEntity sector) {
        this.id = id;
        this.equity = equity;
        this.price = price;
        this.sector = sector;
    }

    public MarketEntity() {
    }

    public long getId() {
        return id;
    }

    public String getEquity() {
        return equity;
    }

    public long getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEquity(String equity) {
        this.equity = equity;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public SectorEntity getSector() {
        return sector;
    }

    public void setSector(SectorEntity sector) {
        this.sector = sector;
    }
}
