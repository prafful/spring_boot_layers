package com.example.demo.service;

import com.example.demo.model.Market;
import com.example.demo.repository.MarketRepoImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketServiceImpl implements MarketService {

    private final MarketRepoImpl repository;

    public MarketServiceImpl(MarketRepoImpl repository) {
        this.repository = repository;
    }

    @Override
    public List<Market> getAllEquities() {
        return repository.getAllEquities();
    }

    @Override
    public Market getEquityByid(long id) {
        return repository.getEquityByid(id);
    }

    @Override
    public Market saveEquity(Market saveEquity) {
        return repository.saveEquity(saveEquity);
    }
}
