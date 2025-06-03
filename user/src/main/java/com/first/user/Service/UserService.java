package com.first.user.Service;

import com.first.user.DTO.ProductDTO;
import com.first.user.Entity.UserEntity;
import com.first.user.External.Service.ProductService;
import com.first.user.Repository.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.hibernate.engine.internal.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.logging.Logger;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService product;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> addUser(UserEntity userEntity) {
        try {
            userRepository.save(userEntity);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<UserEntity>> getAll() {
        try {
            List<UserEntity> users = userRepository.findAll();
            for (int i =0; i < users.size(); i++) {
                List<ProductDTO> products = product.getProduct(users.get(i).getID());
                users.get(i).setProducts(products);
            }
            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<UserEntity> getUserById(long id) {
        try {
            return new ResponseEntity<>(userRepository.findById(id).orElse(null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

// Delete API to remove record from Database
    public ResponseEntity<String> deleteById(long id) {
        try {
            String MSG = "User Removed with ID :" + id;
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
            } else {
                MSG = " User Don't exists with ID : " + id;
            }
            return new ResponseEntity<>(MSG, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Fetching Product name from userID - REST TEMPLATE
    @CircuitBreaker(name = "productBreaker", fallbackMethod = "productBreaker")
    public ResponseEntity<String> getProductName(long id) {
        try {
            String product = null;
            if (userRepository.existsById(id)) {
                product = restTemplate.getForObject("http://localhost:8030/product/username/" + id, String.class);
                return new ResponseEntity<>(product, HttpStatus.OK);
            } else {
                product = " Product Don't exists with ID : " + id;
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> productBreaker(long id, Exception ex) {
        String product = "Fallback is executed : " + ex.getMessage();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    // fetching User Details from UserID - RestTemplate
    public ResponseEntity<UserEntity> getUserByUserId(long id) {
        try {
//            Optional<UserEntity> user = userRepository.findById(id);

            return new ResponseEntity<>(userRepository.findById(id).orElse(null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
//_________________________________________________________________________________________________________

    public UserEntity getUserWithProduct(long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        ArrayList<ProductDTO> product = restTemplate.getForObject(
                "http://localhost:8030/product/username/" + id,
                ArrayList.class
        );

        UserEntity user = null;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.setProducts(product);
        }

        return user;
    }

    public ResponseEntity<String> addProduct(long id, ProductDTO products) {
        try {
                product.addProduct(products);
//            userRepository.save(userEntity);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

