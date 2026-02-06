package com.nadeem.journalApp.controller;

import com.nadeem.journalApp.entity.JournalEntry;
import com.nadeem.journalApp.entity.User;
import com.nadeem.journalApp.repository.UserRepository;
import com.nadeem.journalApp.services.JournalEntryService;
import com.nadeem.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user") // what this does is applies path to this class. Thud any mapping in this class will have /jounral/<path..,>
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        try{
            return new ResponseEntity<>(userService.allUsers(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("{userId}")
    public ResponseEntity<?> userById(@RequestBody ObjectId userId){
        try{
            Optional<User> optionalUser = userService.getUserById(userId);
            if(optionalUser.isPresent()){
                return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        try{
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
        //SecurityContextHolder holds the details about login process.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User userInDB = userService.getUserByUsername(userName);
        if(userInDB != null){
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            userService.saveUser(userInDB);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();

            User user = userService.getUserByUsername(userName);
            userService.deleteUser(user.getId());

            return new ResponseEntity<>("Your account has been deleted successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Some problem occured while deleting your account", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
