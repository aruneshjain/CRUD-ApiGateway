package com.first.order.Controller;

import com.first.order.Entity.Orders;
import com.first.order.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
//    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private OrderService orderService;

    @GetMapping("get")
    public ResponseEntity<List<Orders>> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable long id){
        return orderService.getOrderById(id);
    }

    @PostMapping("add")
    public ResponseEntity<String> add(@RequestBody Orders order){
        return orderService.addOrder(order);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id){

        return orderService.deleteById(id);
    }

    // # Getting User details and product Details with feign Client.
//    @GetMapping("")
//    public ResponseEntity<String> getUserName(@PathVariable long uid,@PathVariable long pid){
//        return orderService.getOrderDetails(id);
//    }

}

