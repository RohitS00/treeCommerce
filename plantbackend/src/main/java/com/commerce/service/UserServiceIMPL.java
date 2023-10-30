package com.commerce.service;

import com.commerce.DAO.ConsumerRepository;
import com.commerce.DAO.ProviderRepository;
import com.commerce.DAO.UserRepository;
import com.commerce.entity.Consumer;
import com.commerce.entity.Provider;
import com.commerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceIMPL implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    ConsumerRepository consumerRepository;
    @Autowired
    ProviderRepository providerRepository;
    @Override
    public User register(User user) throws Exception {
        User obj = userRepository.findByEmail(user.getEmail()); //find if user exists with this email
        Consumer consumer = new Consumer(); //create objects for both consumer and provider
        consumer.setUser(user); //setting user property of consumer
        Provider provider = new Provider();
        if (obj != null) {
            throw new Exception("service provider already exist");
        }
        User returnValue = userRepository.save(user);
        if(Objects.equals(user.getRole(), "consumer")){
            System.out.println("consumer is working");
            consumerRepository.save(consumer); // save to database
        } else if (Objects.equals(user.getRole(), "provider")) {
            provider.setUser(user);
            providerRepository.save(provider);
        }
        return returnValue;
    }

    @Override
    public User login(User user) throws Exception {
        User obj= userRepository.findByEmail(user.getEmail());
        if(obj==null) {
            throw new Exception("User didn't exists");
        }
        else if(!user.getPassword().equals(obj.getPassword())) {
            throw new Exception("Bad credentials");
        }

        return obj;
    }
}
