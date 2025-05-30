package com.first.product.Entity;

import com.first.product.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_type")
    private String productType;
    @Column(name = "price")
    private String price;
    @Column(name = "user_id")
    private long userId;
}
