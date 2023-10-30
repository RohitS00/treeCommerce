package com.commerce.service;

import com.commerce.DAO.ProviderRepository;
import com.commerce.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceIMPL implements ProviderService{
    @Autowired
    ProviderRepository providerRepository;
    @Override
    public Provider findProvider(int id) {
        return providerRepository.findById((long) id).orElse(null);
    }
}
