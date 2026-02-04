package com.example.demo.service;

import com.example.demo.model.Market;

import java.util.List;

public interface MarketService {
    List<Market> getAllEquities();

    Market getEquityByid(long id);

    Market saveEquity(Market saveEquity);

    //today

    Market updateEquity(long id, Market m);

    void deleteEquity(long id);

    List<Market> getBySector(Long sectorId);



}
