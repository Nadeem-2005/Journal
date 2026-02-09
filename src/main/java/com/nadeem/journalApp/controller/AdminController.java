package com.nadeem.journalApp.controller;


import com.nadeem.journalApp.entity.User;
import com.nadeem.journalApp.repository.UserRepository;
import com.nadeem.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers(){
        try{
            return new ResponseEntity<>(userService.allUsers(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user){
        try{

        userService.saveAdmin(user);
        return new ResponseEntity<>("Created admin successfully", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
