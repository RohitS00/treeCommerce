package com.commerce.service;

import com.commerce.DAO.*;
import com.commerce.entity.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PlantServiceIMPL implements PlantService{


    @Autowired
    ProviderService providerService;

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    private ConsumerRepository consumerRepository;
    @Override
    public Plant savePlant(int id, Plant plant) {
        Provider provider = providerService.findProvider(id);

        if (provider != null) {
            plant.setProvider(provider);

            Stock stock = new Stock();
            stock.setOnHand(plant.getOnHandValue()); // Set the onHand value provided by the provider in plant entity
            stock.setSold(0);// Seting initial sold value to 0

            // Set the stock for the plant
            plant.setStock(stock);

            plantRepository.save(plant);
            List<Plant> tempPlantList = provider.getPlantList();
            tempPlantList.add(plant);
            stock.setPlantList(tempPlantList);
            provider.setPlantList(tempPlantList);
            return plant;
        } else {
            return null;
        }
    }

    @Override
    public List<Plant> getPlants() {
        return plantRepository.findAll();
    }


    }

