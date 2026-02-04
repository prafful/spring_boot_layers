package com.example.demo.bootstrap;

import com.example.demo.entity.MarketEntity;
import com.example.demo.entity.SectorEntity;
import com.example.demo.repository.MarketJPARepository;
import com.example.demo.repository.SectorJPARepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("prod")
@Component
public class MarketDataLoaderProd implements CommandLineRunner {

    private final MarketJPARepository marketRepo;
    private final SectorJPARepository sectorRepo;


    public MarketDataLoaderProd(MarketJPARepository repo, MarketJPARepository marketRepo, SectorJPARepository sectorRepo) {
        this.marketRepo = marketRepo;
        this.sectorRepo = sectorRepo;
    }

    @Override
    public void run(String... args) {
        // "first run" logic: only seed if table is empty
        if (marketRepo.count() > 0) return;

        SectorEntity it = sectorRepo.save(new SectorEntity(null, "PIT"));
        SectorEntity auto = sectorRepo.save(new SectorEntity(null, "PAutomobile"));


        marketRepo.saveAll(List.of(
                new MarketEntity(null, "PTCS", 1232, it),
                new MarketEntity(null, "PINFY", 461, it),
                new MarketEntity(null, "PTATAMOTORS", 8456, auto),
                new MarketEntity(null, "PM&M", 8986, auto)
        ));
    }
}
