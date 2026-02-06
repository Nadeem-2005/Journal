package com.nadeem.journalApp.services;

import com.nadeem.journalApp.entity.User;
import com.nadeem.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User user){

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );
        userRepository.save(user);
    }

    public List<User> allUsers(){
        return new ArrayList<User>(userRepository.findAll());
    }

    public Optional<User> getUserById(ObjectId userId){
        return userRepository.findById(userId);
    }

    public void deleteUser(ObjectId userId){
        userRepository.deleteById(userId);
    }

    public User getUserByUsername(String userName){
        return userRepository.findByUserName(userName);
    }
}
