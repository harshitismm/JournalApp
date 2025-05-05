package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;


//    @GetMapping
//    public ResponseEntity<List<User>> getUser(){
//        List<User> users = service.getAllUsers();
//        if(!users.isEmpty()){
//            return new ResponseEntity<>(users,HttpStatus.FOUND);
//        }else{
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    @GetMapping("/id/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable ObjectId id){
//        User user = service.findById(id).get();
//        if(user.getId()!=null){
//            return new ResponseEntity<>(user,HttpStatus.FOUND);
//        }else{
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userIdDb = service.findByUsername(authentication.getName());
        userIdDb.setUsername(user.getUsername());
        userIdDb.setPassword(user.getPassword());
        service.createUser(userIdDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userIdDb = service.findByUsername(authentication.getName());
        service.deleteUser(userIdDb.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
