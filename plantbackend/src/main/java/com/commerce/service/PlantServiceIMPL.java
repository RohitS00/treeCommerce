package com.commerce.service;

import com.commerce.DAO.*;
import com.commerce.entity.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantServiceIMPL implements PlantService{


    @Autowired
    ProviderService providerService;

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ConsumerRepository consumerRepository;
    @Override
    public Plant savePlant(int id, Plant plant) {
        User user = userRepository.findById((long) id).orElseThrow(() -> new EntityNotFoundException("Consumer not found"));
        Provider provider = user.getProvider();

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

    @Override
    public Optional<Plant> getPlantsById(Long id) {
        return plantRepository.findById(id);
    }


}

