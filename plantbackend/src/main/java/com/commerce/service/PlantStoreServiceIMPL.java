package com.commerce.service;

import com.commerce.DAO.*;
import com.commerce.entity.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlantStoreServiceIMPL implements PlantStoreService{
    @Autowired
    ConsumerRepository consumerRepository;

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    OrderitemRepository orderitemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    @Override
    @Transactional
    public Plant buyPlant(Long userId, Long plantId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Consumer not found")); //if wannt use userId do this
        Consumer consumer = user.getConsumer();
        if (consumer == null){
            System.out.println("consumer nhi hai");
        }
        if (consumer.getPurchaseOrder() == null) {
            consumer.setPurchaseOrder(new ArrayList<>());
        }
        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new EntityNotFoundException("Plant not found"));
//        Consumer consumer = consumerRepository.findById(consumerId).orElseThrow(() -> new EntityNotFoundException("Consumer not found"));

        Stock stock = plant.getStock();

        if (stock.getOnHand() < quantity) {
            throw new IllegalArgumentException("Not enough plants in stock.");
        }

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setConsumer(consumer);
        purchaseOrder.setCost(plant.getPrice() * quantity);

        Orderitem orderitem = new Orderitem();
        orderitem.setQuantity(quantity);
        orderitem.setPurchaseOrder(purchaseOrder);
        orderitem.setPlant(plant);

        plant.setOnHandValue(plant.getOnHandValue() - quantity);
        stock.setOnHand(stock.getOnHand() - quantity);
        stock.setSold(stock.getSold() + quantity);

        consumer.getPurchaseOrder().add(purchaseOrder);

        consumerRepository.save(consumer);
        plantRepository.save(plant);
        purchaseOrderRepository.save(purchaseOrder);
        orderitemRepository.save(orderitem);
        stockRepository.save(stock);
        return plant;
    }

    @Override
    public Plant addToCart(Long userId, Long plantId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Consumer not found"));
        Consumer consumer = user.getConsumer();
        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new EntityNotFoundException("Plant not found"));
//        Consumer consumer = consumerRepository.findById(consumerId).orElseThrow(() -> new EntityNotFoundException("Consumer not found"));
        Stock stock = plant.getStock();
        if (consumer == null){
            System.out.println("consumer nhi hai");
        }
        if (stock.getOnHand() < quantity) {
            throw new IllegalArgumentException("Not enough plants in stock.");
        }
        Cart cart = consumer.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setConsumer(consumer);
            cartRepository.save(cart); // Save the new cart to the database
        }
        stock.setInCart(stock.getInCart()+quantity); //increment inCart value of stock
        Cartitem cartItem = new Cartitem();
        cartItem.setQuantity(quantity);
        cartItem.setPlant(plant);
        cartItem.setCart(cart);

        cartItemRepository.save(cartItem); // Save the new cart item to the database
        return plant;
    }

}
