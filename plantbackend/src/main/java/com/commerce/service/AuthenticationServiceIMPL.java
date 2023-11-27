package com.commerce.service;

import com.commerce.DAO.ConsumerRepository;
import com.commerce.DAO.ProviderRepository;
import com.commerce.DAO.UserRepository;
import com.commerce.controller.Security.JwtAuthenticationResponse;
import com.commerce.controller.Security.SignUpRequest;
import com.commerce.controller.Security.SigninRequest;
import com.commerce.entity.Consumer;
import com.commerce.entity.Provider;
import com.commerce.entity.Role;
import com.commerce.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import static com.commerce.entity.Role.CONSUMER;
import static com.commerce.entity.Role.PROVIDER;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceIMPL implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConsumerRepository consumerRepository;
    private final ProviderRepository providerRepository;
    private static final Logger logger = Logger.getLogger(AuthenticationServiceIMPL.class.getName());
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder().username(request.getUsername()).phoneNumber(request.getPhoneNumber())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole()).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);

        Optional<User> obj = userRepository.findByEmail(user.getEmail()); //find if user exists with this email
        Consumer consumer = new Consumer(); //create objects for both consumer and provider
        consumer.setUser(user); //setting user property of consumer
        Provider provider = new Provider();
        if (obj != null) {
//            throw new Exception("service provider already exist");
        }
        User returnValue = userRepository.save(user);
        if(Objects.equals(user.getRole(), CONSUMER)){
            System.out.println("consumer is working");
            consumerRepository.save(consumer); // save to database
        } else if (Objects.equals(user.getRole(), PROVIDER)) {
            provider.setUser(user);
            providerRepository.save(provider);
        }


        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        System.out.println(request.getEmail());
        System.out.println(request.getPassword());

        authenticationManager.authenticate(
             new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).id(user.getId()).role(user.getRole()).build();

    }
}
