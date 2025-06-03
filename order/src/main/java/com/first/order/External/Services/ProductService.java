package com.first.order.External.Services;

import com.first.order.DTO.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE", path = "product")
public interface ProductService {
    @GetMapping("get/{id}")
    ProductDTO getProduct(@PathVariable long id);
}
