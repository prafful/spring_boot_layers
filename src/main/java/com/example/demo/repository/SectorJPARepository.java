package com.example.demo.repository;

import com.example.demo.entity.SectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectorJPARepository extends JpaRepository<SectorEntity, Long> {
    Optional<SectorEntity> findByNameIgnoreCase(String name);


}
