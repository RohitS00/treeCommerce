package com.commerce.service;

import com.commerce.entity.Plant;

import java.util.List;
import java.util.Optional;

public interface PlantService {
    Plant savePlant(int id, Plant plant);

    List<Plant> getPlants();

    Optional<Plant> getPlantsById(Long id);
}
