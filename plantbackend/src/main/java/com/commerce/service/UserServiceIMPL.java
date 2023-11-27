package com.commerce.service;

import com.commerce.DAO.ConsumerRepository;
import com.commerce.DAO.ProviderRepository;
import com.commerce.DAO.UserRepository;
import com.commerce.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.commerce.entity.Role.CONSUMER;
import static com.commerce.entity.Role.PROVIDER;

@Service
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    ConsumerRepository consumerRepository;
    @Autowired
    ProviderRepository providerRepository;
    @Override
    public User register(User user) throws Exception {
        Optional<User> obj = userRepository.findByEmail(user.getEmail()); //find if user exists with this email
        Consumer consumer = new Consumer(); //create objects for both consumer and provider
        consumer.setUser(user); //setting user property of consumer
        Provider provider = new Provider();
        if (obj != null) {
            throw new Exception("service provider already exist");
        }
        User returnValue = userRepository.save(user);
        if(Objects.equals(user.getRole(), CONSUMER)){
            System.out.println("consumer is working");
            consumerRepository.save(consumer); // save to database
        } else if (Objects.equals(user.getRole(), PROVIDER)) {
            provider.setUser(user);
            providerRepository.save(provider);
        }
        return returnValue;
    }

    @Override
    public Optional<User> login(User user) throws Exception {
        Optional<User> obj= userRepository.findByEmail(user.getEmail());
        if(obj==null) {
            throw new Exception("User didn't exists");
        }
        else if(!user.getPassword().equals(obj.getClass())) {
            throw new Exception("Bad credentials");
        }

        return obj;
    }


    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return (UserDetails) userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public List<Plant> getProviderPlantsById(Long id) {
        Provider provider = userRepository.findById(id).get().getProvider();
        return provider.getPlantList();
    }

    @Override
    public List<PurchaseOrder> getOrderById(Long id) {
        Consumer consumer = userRepository.findById(id).get().getConsumer();
        return consumer.getPurchaseOrder();
    }
}
