package com.first.order.External.Services;

import com.first.order.DTO.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@FeignClient(name = "http://localhost:8020/user")
@FeignClient(name = "USER-SERVICE", path = "user")
public interface UserService {
    @GetMapping("get/{id}")
    UserDTO getUser(@PathVariable long id);
}