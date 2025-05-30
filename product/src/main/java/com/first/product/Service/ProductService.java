package com.first.product.Service;

import com.first.product.Entity.ProductEntity;
import com.first.product.External.Services.UserService;
import com.first.product.Repository.ProductRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    public ResponseEntity<String> addProduct(ProductEntity productEntity) {
        try {
            productRepository.save(productEntity);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<ProductEntity>> getAll() {
        try {
            return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ProductEntity> getProductById(long id) {
        try{
            ProductEntity product = productRepository.findById(id).orElse(null);
            return new ResponseEntity<>(product,HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteById(long id) {
        try{
            String MSG = "Product Removed with ID :" + id;
            if(productRepository.existsById(id)) {
                productRepository.deleteById(id);
            }
            else{
                MSG = " Product Don't exists with ID : " + id;
            }
            return new ResponseEntity<>(MSG,HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // Fetching Product name from userID - REST TEMPLATE
    public ResponseEntity<List<ProductEntity>> getProductByUserId(long id) {
        try{
                ArrayList<ProductEntity> product = (ArrayList<ProductEntity>) productRepository.findByUserId(id);
               return new ResponseEntity<>(product,HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // fetching User Details from UserID - FEIGN CLIENT
    @RateLimiter(name = "userRateLimiter" , fallbackMethod = "userBreaker")
    public ResponseEntity<String> getUserName(long id) {
        String product = userService.getUser(id).getBody();
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    public ResponseEntity<String> userBreaker(long id,Exception ex){
        String product = "Fallback is executed : " + ex.getMessage();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}