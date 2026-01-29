package com.example.demo.bootstrap;


import com.example.demo.entity.MarketEntity;
import com.example.demo.repository.MarketJPARepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final MarketJPARepository repository;

    public DataLoader(MarketJPARepository repository) {
        this.repository = repository;
    }


    @Override
    public void run(String... args) throws Exception {
        //check if repo is empty
        if (repository.count() > 0)return;

        repository.saveAll(List.of(
           new MarketEntity(null, "HGFHJ", 5785),
                new MarketEntity(null, "TYTI", 64) ,
                new MarketEntity(null, "VDFN", 8941),
                new MarketEntity(null, "PKKJ", 124)
        ));
    }
}
