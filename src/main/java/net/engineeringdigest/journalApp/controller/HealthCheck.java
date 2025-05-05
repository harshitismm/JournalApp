package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class HealthCheck {

    @Autowired
    private UserService service;

    @GetMapping("/status")
    public String healthCheck(){
        return "Application is running fine ";
    }
    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody User user){

        service.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
