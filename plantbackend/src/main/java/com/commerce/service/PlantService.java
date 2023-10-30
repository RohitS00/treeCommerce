package com.commerce.service;

import com.commerce.entity.Plant;

import java.util.List;

public interface PlantService {
    Plant savePlant(int id, Plant plant);

    List<Plant> getPlants();
}
