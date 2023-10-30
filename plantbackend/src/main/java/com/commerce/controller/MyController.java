package com.commerce.controller;

import com.commerce.DAO.PlantRepository;
import com.commerce.entity.Plant;
import com.commerce.entity.User;
import com.commerce.service.PlantService;
import com.commerce.service.PlantStoreService;
import com.commerce.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MyController {
    @Autowired
    UserService userService;

    @Autowired
    PlantService plantService;

    @Autowired
    PlantStoreService plantStoreService;
    @Autowired
    PlantRepository plantRepository;
    @Value("${plant.picture.upload.directory}")
    private String localStoragePath;

    @PostMapping("/registerUser") //signup
    public User registerUser(@RequestBody User user) throws Exception {
        return userService.register(user);
    }

    @PostMapping("/loginUser") //login
    public User loginUser(@RequestBody User user) throws Exception {
        return userService.login(user);
    }

    @PostMapping("/addPlant/{Pid}") //uploading plant by provider
    public Plant addPlant(@PathVariable("Pid") int id, @RequestBody Plant plant) {

        return plantService.savePlant(id, plant);
    }

    @GetMapping("/") //seeing all plants
    public List<Plant> getAllPlants() {
        return plantService.getPlants();
    }

    @PostMapping("/buy-plant/{consumerId}/{plantId}/{quantity}") //for consumer to buy plant
    public ResponseEntity<String> buyPlant(@PathVariable Long consumerId,
                                           @PathVariable Long plantId,
                                           @PathVariable int quantity) {
        try {
            plantStoreService.buyPlant(consumerId, plantId, quantity);
            return ResponseEntity.ok("Purchase successful!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/add-to-cart/{consumerId}/{plantId}/{quantity}") //for consumer to add to a plant to cart
    public ResponseEntity<String> addToCart(@PathVariable Long consumerId, @PathVariable Long plantId, @PathVariable int quantity) {
        try {
            plantStoreService.addToCart(consumerId, plantId, quantity);
            return ResponseEntity.ok("Added to cart");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/uploadPlantPicture/{Pid}/{plantId}")
    public void uploadPicture(@RequestParam("file") MultipartFile file, @PathVariable Long plantId) {
        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new EntityNotFoundException("Plant not found"));
        try {
            // Handle the uploaded file
            byte[] fileBytes = file.getBytes();
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path storageDirectory = Paths.get(localStoragePath);
            Path filePath = storageDirectory.resolve(fileName);

            // Ensure the directory exists
            File directory = new File(storageDirectory.toString());
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Save the file
            file.transferTo(filePath.toFile());

            // Save the file to a location or store it in a database if necessary
            // For example, you can use file.transferTo(new File("/path/to/save/file.jpg"));
            // Or store it in a database depending on your requirements

            // Set the 'picture' field of the plant entity with the file path or identifier
            // For example, if you're storing the file path:


        plant.setPicture(String.valueOf(filePath));
            plantRepository.save(plant);// Modify this with the actual path or identifier

        } catch (IOException e) {
            // Handle exception related to file upload
            e.printStackTrace(); // You might want to log the error or return an appropriate response

        }
    }

}
