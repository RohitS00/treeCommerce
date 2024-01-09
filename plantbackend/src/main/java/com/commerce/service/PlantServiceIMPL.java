package com.commerce.service;

import com.commerce.DAO.*;
import com.commerce.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
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

    @Override
    public ResponseEntity<Plant> addPlant(int id, String plant1, MultipartFile image) {
        ObjectMapper objectMapper = new ObjectMapper();
        Plant plant = null;
        User user = userRepository.findById((long) id).orElseThrow(() -> new EntityNotFoundException("Consumer not found"));
        Provider provider = user.getProvider();
        try {
            plant = objectMapper.readValue(plant1, Plant.class);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        if (provider != null) {
            plant.setProvider(provider);

            Stock stock = new Stock();
            stock.setOnHand(plant.getOnHandValue()); // Set the onHand value provided by the provider in plant entity
            stock.setSold(0);// Seting initial sold value to 0

            // Set the stock for the plant
            plant.setStock(stock);
            //setting image
            try {
                byte[] imageBytes = image.getBytes();

//                String base64image = Base64.getEncoder().encodeToString(imageBytes);
//                base64image = "data:image/png;base64," + base64image;
                plant.setPictureData(imageBytes);
            }
            catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            plantRepository.save(plant);
            List<Plant> tempPlantList = provider.getPlantList();
            tempPlantList.add(plant);
            stock.setPlantList(tempPlantList);
            provider.setPlantList(tempPlantList);
            return ResponseEntity.ok(plant);
    }
        else {
            return null;
        }

}}

