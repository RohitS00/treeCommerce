package com.commerce.service;

import com.commerce.entity.User;

public interface UserService {
    User register(User user) throws Exception;

    User login(User user) throws Exception;
}
