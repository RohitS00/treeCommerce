package com.commerce.service;

import com.commerce.entity.Plant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PlantService {
    Plant savePlant(int id, Plant plant);

    List<Plant> getPlants();

    Optional<Plant> getPlantsById(Long id);

    ResponseEntity<Plant> addPlant(int id, String plant, MultipartFile image);
}
