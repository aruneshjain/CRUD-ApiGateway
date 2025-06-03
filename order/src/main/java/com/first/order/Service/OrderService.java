package com.first.order.Service;

import com.first.order.DTO.ProductDTO;
import com.first.order.DTO.UserDTO;
import com.first.order.Entity.Orders;
import com.first.order.External.Services.ProductService;
import com.first.order.External.Services.UserService;
import com.first.order.Reposoitory.OrderRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService user;

    @Autowired
    private ProductService product;

    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orderList = orderRepository.findAll();

        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Orders> getOrderById(long id) {
        Orders order = orderRepository.findById(id).get();
        UserDTO userDetails = user.getUser(order.getUserId());
        ProductDTO productDetails = product.getProduct(order.getProductId());
        order.setProducts(productDetails);
        order.setUser(userDetails);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    public ResponseEntity<String> addOrder(Orders order) {
        try {
           // LocalDateTime date = LocalDateTime.now();
            order.setDate(LocalDateTime.now());
            orderRepository.save(order);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteById(long id) {
        try{
            String MSG = "Order Removed with ID :" + id;
            if(orderRepository.existsById(id)) {
                orderRepository.deleteById(id);
            }
            else{
                MSG = " Order Don't exists with ID : " + id;
            }
            return new ResponseEntity<>(MSG,HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
