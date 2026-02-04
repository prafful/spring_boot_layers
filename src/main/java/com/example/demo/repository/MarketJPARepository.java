package com.example.demo.repository;

import com.example.demo.entity.MarketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketJPARepository extends JpaRepository<MarketEntity, Long > {
    List<MarketEntity> findBySectorId(Long sectorId);
}
