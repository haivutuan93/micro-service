package com.eureka.auth.model;

import lombok.Data;

@Data
public class UserRegister {
    String username;
    String password;
    String displayName;
    String mobile;
    String email;
    String address;
}
