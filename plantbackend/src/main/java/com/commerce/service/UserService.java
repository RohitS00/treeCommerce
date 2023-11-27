package com.commerce.service;

import com.commerce.entity.Plant;
import com.commerce.entity.PurchaseOrder;
import com.commerce.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User register(User user) throws Exception;

    Optional<User> login(User user) throws Exception;

    UserDetailsService userDetailsService();

    List<Plant> getProviderPlantsById(Long id);

    List<PurchaseOrder> getOrderById(Long id);
}
