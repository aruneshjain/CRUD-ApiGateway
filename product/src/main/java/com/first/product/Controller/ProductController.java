package com.first.product.Controller;

import com.first.product.Entity.ProductEntity;
import com.first.product.Service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("get")
    public ResponseEntity<List<ProductEntity>> getAll(){
        return productService.getAll();
    }

    @GetMapping("get/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable long id){
        return productService.getProductById(id);
    }

    @PostMapping("add")
    public ResponseEntity<String> add(@RequestBody ProductEntity userEntity){
        return productService.addProduct(userEntity);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id){
        return productService.deleteById(id);
    }
    // Fetching Product name from userID - REST TEMPLATE
    @GetMapping("username/{id}")
    public ResponseEntity<List<ProductEntity>> getProductByUserId(@PathVariable long id){
        return productService.getProductByUserId(id);
    }

    @GetMapping("user/{id}")

    public ResponseEntity<String> getUserName(@PathVariable long id){
        return productService.getUserName(id);
    }
}