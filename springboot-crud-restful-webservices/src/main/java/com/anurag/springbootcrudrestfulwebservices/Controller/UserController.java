package com.anurag.springbootcrudrestfulwebservices.Controller;
import org.springframework.http.ResponseEntity;
import com.anurag.springbootcrudrestfulwebservices.Entity.User;
import com.anurag.springbootcrudrestfulwebservices.Exception.ResourceNotFoundException;
import com.anurag.springbootcrudrestfulwebservices.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    //For testing
    @GetMapping("/hi")
    public String msg(){
        return "Hello!";
    }

    //for create user
    @PostMapping("/users")
    public void createUser(@RequestBody User user){
        userRepository.saveAll(Collections.singletonList(user));
    }

    //for getting user
    @GetMapping("/users")
    public List<User> getUser(){
        return userRepository.findAll();
    }

    @GetMapping("/userById/{id}")
    public User getUserById(@PathVariable (value = "id") long userId){

        return this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable ("id") long userId){
        User existingUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }

}
