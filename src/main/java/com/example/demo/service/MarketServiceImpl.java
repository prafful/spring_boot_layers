package com.example.demo.service;

import com.example.demo.entity.MarketEntity;
import com.example.demo.model.Market;
import com.example.demo.repository.MarketJPARepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarketServiceImpl implements MarketService {

    //private final MarketRepoImpl repository;
    private final MarketJPARepository repository;

    public MarketServiceImpl(MarketJPARepository repository) {
        this.repository = repository;
    }


//    public MarketServiceImpl(MarketRepoImpl repository) {
//        this.repository = repository;
//    }

    public static Market toModel(MarketEntity me){
        return new Market(me.id, me.equity, me.price);
    }

    public static MarketEntity toEntity(Market me){
        return new MarketEntity(me.id==0?null:me.id, me.equity, me.price);
    }



    @Override
    public List<Market> getAllEquities() {
        //return repository.getAllEquities();
        List<MarketEntity> meList =  repository.findAll();
        List<Market> mList = new ArrayList<Market>();
        for (int i = 0; i < meList.size(); i++) {
            //mList.add(new Market(meList.get(i).id, meList.get(i).equity, meList.get(i).price));
            //better alternative is to use helpper method -> toModel
            mList.add(toModel(meList.get(i)));
        }
        return mList;
    }

    @Override
    public Market getEquityByid(long id) {
        //return repository.getEquityByid(id);
        MarketEntity meByid =  repository.findById(id).orElseThrow();
        return toModel(meByid);
    }

    @Override
    public Market saveEquity(Market saveEquity) {
        //return repository.saveEquity(saveEquity);
        //saveEquity.id = 0;
        //MarketEntity newMe = new MarketEntity(null,saveEquity.equity, saveEquity.price);
        MarketEntity addedMe = repository.save(toEntity(saveEquity));
        return toModel(addedMe);
    }

    @Override
    public Market updateEquity(long id, Market m) {
        //check if equity by id is present and fetch it
        MarketEntity me = repository.findById(id).orElseThrow();
        //update fetched one with new value
        me.equity = m.equity;
        me.price = m.price;
        //save it again
        MarketEntity meUpdated = repository.save(me);
        //convert it to model instance and return
        return toModel(meUpdated);
    }

    @Override
    public void deleteEquityById(long id) {
        //check if equity with given id will exist
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equity not found: " + id);
        }
        repository.deleteById(id);

        //delete it
    }


}
