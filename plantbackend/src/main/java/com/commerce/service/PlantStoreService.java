package com.commerce.service;

import com.commerce.entity.Plant;

public interface PlantStoreService { //for ordering and adding to cart
    Plant buyPlant(Long userId, Long plantId, int quantity);
    Plant addToCart(Long userId, Long plantId, int quantity);
}
