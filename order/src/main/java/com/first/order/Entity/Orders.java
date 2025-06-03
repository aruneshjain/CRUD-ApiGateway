package com.first.order.Entity;

import com.first.order.DTO.ProductDTO;
import com.first.order.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "order_number")
    private long orderNumber;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "product_id")
    private long productId;

    @Transient
    private ProductDTO products;

    @Transient
    private UserDTO user;
}
