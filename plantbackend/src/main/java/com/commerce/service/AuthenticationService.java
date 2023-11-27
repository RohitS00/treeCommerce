package com.commerce.service;

import com.commerce.controller.Security.JwtAuthenticationResponse;
import com.commerce.controller.Security.SignUpRequest;
import com.commerce.controller.Security.SigninRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
