package com.example.demo.restcontroller;

import com.example.demo.entity.SectorEntity;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Sector;
import com.example.demo.repository.SectorJPARepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/market/1.0/sectors")
public class SectorRestController {

    private final SectorJPARepository sectorRepo;

    public SectorRestController(SectorJPARepository sectorRepo) {
        this.sectorRepo = sectorRepo;
    }

    private static Sector toModel(SectorEntity e) {
        return new Sector(e.getId(), e.getName());
    }

    @GetMapping
    public List<Sector> getAllSectors() {
        return sectorRepo.findAll().stream().map(SectorRestController::toModel).toList();
    }

    @GetMapping("/{id}")
    public Sector getSector(@PathVariable long id) {
        SectorEntity e = sectorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Sector not found: " + id));
        return toModel(e);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Sector createSector(@RequestBody Sector s) {
        System.out.println("####################################################");
        System.out.println(s.name);
        if (s.name == null || s.name.trim().isEmpty()) {
            throw new BadRequestException("sector name is required");
        }

        sectorRepo.findByNameIgnoreCase(s.name.trim())
                .ifPresent(x -> { throw new BadRequestException("Sector already exists: " + s.name); });

        SectorEntity saved = sectorRepo.save(new SectorEntity(null, s.name.trim()));
        return toModel(saved);
    }
}
