package com.example.demo.Security;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSecurity userSecurity = new UserSecurity();
        User user = null; //= userRepository.findAllByName(username);
        List<User> listUsers = userRepository.findAll();
        for(User u : listUsers) {
            if(u.getName().equals(username)) {
                user = u;
            }
        }

        System.gc();
        System.out.println("\nfreMemory = " +
                Runtime.getRuntime().freeMemory() + "|||" + Runtime.getRuntime().maxMemory() + "|||" +
                (Runtime.getRuntime().maxMemory() / Runtime.getRuntime().freeMemory()));

        if(user == null) {
            throw new UsernameNotFoundException("User not found in database: " + username);
        }
        userSecurity.setUsername(user.getName());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        userSecurity.setPassword(user.getPassword()); //(encoder.encode(user.getPassword()));


        ArrayList<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(user.getRole().name()));
        userSecurity.setGrantedAuthoritiesList(authorityList);
        return new CustomUser(userSecurity);

    }
}
