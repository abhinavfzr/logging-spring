package com.stackroute.controller;


import com.stackroute.domain.User;
import com.stackroute.exception.UserAlreadyExistException;
import com.stackroute.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
public class UserController {


  private UserService userService;

  @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("save")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        ResponseEntity responseEntity;
        try {
            userService.saveUser(user);
            responseEntity = new ResponseEntity<String>("Successfully Created", HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            logger.debug("This is a debug message");
            System.out.println("msg" + e.getMessage());
            responseEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
            e.printStackTrace();
            }

            //responseEntity = new ResponseEntity<String>("Successfully Created", HttpStatus.CREATED);
       return responseEntity;

    }


    @GetMapping("users")
    public ResponseEntity<?>getAllUser(){

      return new ResponseEntity<List<User>>(userService.getAllUser() , HttpStatus.OK);
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/log")
    public ResponseEntity<String> fetchAllMovies() {
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }


}
