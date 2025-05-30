package com.first.user.Controller;

import com.first.user.Entity.UserEntity;
import com.first.user.Service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("get")
    public ResponseEntity<List<UserEntity>> getAll(){
        return userService.getAll();
    }

    @GetMapping("get/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }

    @PostMapping("add")
    public ResponseEntity<String> add(@RequestBody UserEntity userEntity){
        return userService.addUser(userEntity);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> add(@PathVariable long id){
        return userService.deleteById(id);
    }

    // Fetching Product name from userID - REST TEMPLATE
    @GetMapping("product/{id}")
    public ResponseEntity<String> getProductName(@PathVariable long id){
        return userService.getProductName(id);
    }


    @GetMapping("productname/{id}")
    public ResponseEntity<UserEntity> getUserByUserId(@PathVariable long id){
        return userService.getUserByUserId(id);
    }
//    ___________________________________________________________________________________________

    @GetMapping("getProduct/{id}")
    public UserEntity getUserWithProducts(@PathVariable long id){
        return userService.getUserWithProduct(id);
    }


}
