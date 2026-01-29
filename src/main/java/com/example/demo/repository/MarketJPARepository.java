package com.example.demo.repository;

import com.example.demo.entity.MarketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketJPARepository extends JpaRepository<MarketEntity, Long> {

}
