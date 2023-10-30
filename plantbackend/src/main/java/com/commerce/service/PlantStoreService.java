package com.commerce.service;

public interface PlantStoreService { //for ordering and adding to cart
    void buyPlant(Long consumerId, Long plantId, int quantity);
    void addToCart(Long userId, Long plantId, int quantity);
}
