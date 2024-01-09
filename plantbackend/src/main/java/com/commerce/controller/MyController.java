package com.commerce.controller;

import com.commerce.DAO.PlantRepository;
import com.commerce.entity.Orderitem;
import com.commerce.entity.Plant;
import com.commerce.entity.Provider;
import com.commerce.entity.PurchaseOrder;
import com.commerce.service.PlantService;
import com.commerce.service.PlantStoreService;
import com.commerce.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

//    @PostMapping("/registerUser") //signup
//    public User registerUser(@RequestBody User user) throws Exception {
//        return userService.register(user);
//    }

//    @PostMapping("/loginUser") //login
//    public Optional<User> loginUser(@RequestBody User user) throws Exception {
//        return userService.login(user);
//    }
//
    @PostMapping("/addPlant/{Uid}") //uploading plant by provider
    public Plant addPlant(@PathVariable("Uid") int id, @RequestBody Plant plant) {

        return plantService.savePlant(id, plant);
    }
//    @PostMapping(path = "/savePlant/{Uid}", consumes = MediaType.ALL_VALUE) //uploading plant along with picture
//    public ResponseEntity<Plant> savePlant(@PathVariable("Uid") int id, @RequestPart("plant") String plant, @RequestPart("image") MultipartFile image) {
//        return this.plantService.addPlant(id,plant,image);
//    }
    @PostMapping(path = "/savePlant", consumes = MediaType.ALL_VALUE) //uploading plant along with picture
    public ResponseEntity<Plant> savePlant(@RequestParam("Uid") int id, @RequestPart("plant") String plant, @RequestPart("image") MultipartFile image) {
        return this.plantService.addPlant(id, plant, image);
    }


    @GetMapping("/") //seeing all plants
    public List<Plant> getAllPlants() {
        return plantService.getPlants();
    }


    @GetMapping("/plants/{Pid}") //finding plant by id
    public Optional<Plant> getPlant(@PathVariable("Pid") Long id){
        return plantService.getPlantsById(id);
    }

    @PostMapping("/buy-plant/{userId}/{plantId}/{quantity}") //for consumer to buy plant
    public ResponseEntity<?>  buyPlant(@PathVariable Long userId,
                                           @PathVariable Long plantId,
                                           @PathVariable int quantity) {
        try {
            Plant purchasedPlant = plantStoreService.buyPlant(userId, plantId, quantity);


            return ResponseEntity.ok(purchasedPlant);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
        }
    }


    @PostMapping("/add-to-cart/{userId}/{plantId}/{quantity}") //for consumer to add to a plant to cart
    public ResponseEntity<?> addToCart(@PathVariable Long userId, @PathVariable Long plantId, @PathVariable int quantity) {
        try {
            Plant plantCart= plantStoreService.addToCart(userId, plantId, quantity);
            return ResponseEntity.ok(plantCart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
        }
    }

    @PostMapping("/uploadPlantPicture/{Uid}/{plantId}")
    public void uploadPicture(@RequestParam("file") MultipartFile file, @PathVariable Long plantId) {
        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new EntityNotFoundException("Plant not found"));
        try {
            // Handle the uploaded file
            byte[] fileBytes = file.getBytes();
//            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//            Path storageDirectory = Paths.get(localStoragePath);
//            Path filePath = storageDirectory.resolve(fileName);
//
//            // Ensure the directory exists
//            File directory = new File(storageDirectory.toString());
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
//
//            // Save the file
//            file.transferTo(filePath.toFile());
            plant.setPictureData(fileBytes);
            plantRepository.save(plant);

            // Save the file to a location or store it in a database if necessary
            // For example, you can use file.transferTo(new File("/path/to/save/file.jpg"));
            // Or store it in a database depending on your requirements

            // Set the 'picture' field of the plant entity with the file path or identifier
            // For example, if you're storing the file path:


//        plant.setPicture(String.valueOf(filePath));
//            plantRepository.save(plant);// Modify this with the actual path or identifier

        } catch (IOException e) {
            // Handle exception related to file upload
            e.printStackTrace(); // You might want to log the error or return an appropriate response

        }
    }

    @GetMapping("/provider/plants/{Uid}") //finding plant by id
    public List<Plant> getPlantByProvider(@PathVariable("Uid") Long id){
        return userService.getProviderPlantsById(id);
    }
    @GetMapping("/consumer/plants/{Uid}") //finding plant by id
    public List<PurchaseOrder> getOrders(@PathVariable("Uid") Long id){
        return userService.getOrderById(id);
    }

}
