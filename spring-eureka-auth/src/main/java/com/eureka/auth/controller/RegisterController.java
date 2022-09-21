package com.eureka.auth.controller;

import com.eureka.auth.entity.User;
import com.eureka.auth.model.UserRegister;
import com.eureka.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RegisterController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserRegister request){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        String encodedPassword = new BCryptPasswordEncoder().encode(request.getPassword());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(encodedPassword);
        user.setDisplayName(request.getDisplayName());
        user.setMobile(request.getMobile());
        user.setMobile(request.getMobile());
        user.setAddress(request.getAddress());
        userRepository.save(user);

        return user.getUsername();
    }
}
