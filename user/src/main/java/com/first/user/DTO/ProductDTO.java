package com.first.user.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {

    private long id;
    private String productName;
    private String productType;
    private String price;
    private long userId;
}
