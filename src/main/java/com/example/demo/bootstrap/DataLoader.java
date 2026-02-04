package com.example.demo.bootstrap;


import com.example.demo.entity.MarketEntity;
import com.example.demo.entity.SectorEntity;
import com.example.demo.repository.MarketJPARepository;
import com.example.demo.repository.SectorJPARepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final MarketJPARepository marketRepo;
    private final SectorJPARepository sectorRepo;

    public DataLoader(MarketJPARepository marketRepo, SectorJPARepository sectorRepo) {
        this.marketRepo = marketRepo;
        this.sectorRepo = sectorRepo;
    }


    @Override
    public void run(String... args) throws Exception {
        //check if repo is empty
        if (marketRepo.count() > 0)return;
        SectorEntity it = sectorRepo.save(new SectorEntity(null, "IT"));
        SectorEntity auto = sectorRepo.save(new SectorEntity(null, "Automobile"));


        marketRepo.saveAll(List.of(
                new MarketEntity(null, "TCS", 1232, it),
                new MarketEntity(null, "INFY", 461, it),
                new MarketEntity(null, "TATAMOTORS", 8456, auto),
                new MarketEntity(null, "M&M", 8986, auto)
        ));
    }
}
