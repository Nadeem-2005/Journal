package com.nadeem.journalApp.services;

import com.nadeem.journalApp.entity.User;
import com.nadeem.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    /**
     * This method is automatically called by Spring Security during the authentication (login) process.
     * When a user attempts to log in, Spring extracts the username and invokes this method to load
     * the corresponding user details from the database.
     *
     * The method fetches the User entity using the username and converts it into a Spring Security
     * UserDetails object, which contains the encrypted password and assigned roles.
     *
     * After this method returns UserDetails, Spring Security internally compares the entered
     * (raw) password with the stored encrypted password using the configured PasswordEncoder
     * (e.g., BCryptPasswordEncoder). The comparison is done using passwordEncoder.matches(),
     * ensuring that plain-text passwords are never stored or compared directly.
     *
     * If the user is not found in the database, a UsernameNotFoundException is thrown, which causes
     * the authentication process to fail.
     *
     * This method is executed on every login attempt and is NOT used during user registration
     * or for checking whether a username already exists. Registration and validation logic
     * must be handled separately in the service layer.
     */

    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user != null){
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0] ))
                    .build();

            return userDetails;

        }
        throw new UsernameNotFoundException("User not found with username : "+username);
    }
}
