package com.first.product.External.Services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "http://localhost:8020/user")
@FeignClient(name = "USER-SERVICE", path = "user")
public interface UserService {
    @GetMapping("productname/{id}")
    ResponseEntity<String> getUser(@PathVariable long id);
}
