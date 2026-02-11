package com.nadeem.journalApp.controller;


import com.nadeem.journalApp.entity.User;
import com.nadeem.journalApp.services.CustomUserDetailService;
import com.nadeem.journalApp.services.UserService;
import com.nadeem.journalApp.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    /**
     *
     * Wherever we would lke to implement logging functionality  (slf4j)
     * we use the below line of code to create an instacne of the logger we want to use
     * the access modifier would private ,static(to create only one instance for the whole class), final (to prevent any accidental modifications)
     */

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class); // we input the class name we would like to implement

    @Autowired
    private UserService userService;


    //should be exposed in security config using @Bean notation
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody User user){
        try{
            userService.saveUser(user);
            return new ResponseEntity<>("Signup was successful", HttpStatus.CREATED);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user){
        try{
            //internally calls CustomUserDetailservice and passwordencoder to check
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = customUserDetailService.loadUserByUsername(user.getUserName());
            String token = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occured : ", e);
            return new ResponseEntity<>("Invalid credentials", HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
