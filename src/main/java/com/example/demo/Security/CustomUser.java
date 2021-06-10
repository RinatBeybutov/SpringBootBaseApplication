package com.example.demo.Security;

import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

    public CustomUser(UserSecurity userSecurity) {
        super(userSecurity.getUsername(), userSecurity.getPassword(), userSecurity.getGrantedAuthoritiesList());
    }

}
