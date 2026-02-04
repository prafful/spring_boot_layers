package com.example.demo.service;

import com.example.demo.entity.MarketEntity;
import com.example.demo.entity.SectorEntity;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Market;
import com.example.demo.repository.MarketJPARepository;
import com.example.demo.repository.MarketRepoImpl;
import com.example.demo.repository.SectorJPARepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarketServiceImpl implements MarketService {

    //private final MarketRepoImpl repository;
    private final MarketJPARepository repository;
    private final SectorJPARepository sectorRepository;

    public MarketServiceImpl(MarketJPARepository repository, SectorJPARepository sectorRepository) {
        this.repository = repository;
        this.sectorRepository = sectorRepository;
    }

    private static Market toModel(MarketEntity e) {
        long sectorId = e.getSector() == null ? 0 : e.getSector().getId();
        String sectorName = e.getSector() == null ? null : e.getSector().getName();

        return new Market(
                e.getId(),
                e.getEquity(),
                e.getPrice(),
                sectorId,
                sectorName
        );
    }

    private MarketEntity toEntity(Market m) {
        if (m.equity == null || m.equity.trim().isEmpty()) {
            throw new BadRequestException("equity is required");
        }

        // Sector must exist (because MarketEntity.sector is non-nullable)
        if (m.sectorId <= 0) {
            throw new BadRequestException("sectorId is required (must be > 0)");
        }

        SectorEntity sector = sectorRepository.findById(m.sectorId)
                .orElseThrow(() -> new NotFoundException("Sector not found: " + m.sectorId));

        return new MarketEntity(
                (m.id == 0 ? null : m.id),
                m.equity,
                m.price,
                sector
        );
    }


    @Override
    public List<Market> getAllEquities() {
        //List<MarketEntity> ma= repository.findAll();
        List<MarketEntity> entities = repository.findAll();

        List<Market> result = new ArrayList<>(entities.size());
        for (int i = 0; i < entities.size(); i++) {
            result.add(toModel(entities.get(i)));
        }

        return result;
    }

    @Override
    public Market getEquityByid(long id) {
        MarketEntity entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equity not found: " + id));
        return toModel(entity);
    }

    @Override
    public Market saveEquity(Market saveEquity) {

        if (saveEquity.equity == null || saveEquity.equity.trim().isEmpty()) {
            throw new BadRequestException("equity is required");
        }
        // id should be null/0 for create
        saveEquity.id = 0;
        MarketEntity saved = repository.save(toEntity(saveEquity));
        return toModel(saved);
    }

    @Override
    public Market updateEquity(long id, Market m) {
        MarketEntity existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equity not found: " + id));

        if (m.equity == null || m.equity.trim().isEmpty()) {
            throw new BadRequestException("equity is required");
        }
        existing.setEquity(m.equity);
        existing.setPrice(m.price);

        // Optional: allow changing sector on update
        if (m.sectorId > 0) {
            SectorEntity sector = sectorRepository.findById(m.sectorId)
                    .orElseThrow(() -> new NotFoundException("Sector not found: " + m.sectorId));
            existing.setSector(sector);
        }

        MarketEntity saved = repository.save(existing);
        return toModel(saved);
    }

    @Override
    public void deleteEquity(long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Equity not found: " + id);
        }
        repository
                .deleteById(id);
    }

    @Override
    public List<Market> getBySector(Long sectorId) {
        if (sectorId == null || sectorId <= 0) {
            throw new BadRequestException("sectorId must be provided and > 0");
        }

        // Optional: validate sector exists
        sectorRepository.findById(sectorId)
                .orElseThrow(() -> new NotFoundException("Sector not found: " + sectorId));

        List<MarketEntity> entities = repository.findBySectorId(sectorId);
        List<Market> result = new ArrayList<>(entities.size());
        for (MarketEntity e : entities) result.add(toModel(e));
        return result;
    }
    }


//    public MarketServiceImpl(MarketRepoImpl repository) {
//        this.repository = repository;
//    }

//    @Override
//    public List<Market> getAllEquities() {
//        return repository.getAllEquities();
//    }
//
//    @Override
//    public Market getEquityByid(long id) {
//        return repository.getEquityByid(id);
//    }
//
//    @Override
//    public Market saveEquity(Market saveEquity) {
//        return repository.saveEquity(saveEquity);
//    }

