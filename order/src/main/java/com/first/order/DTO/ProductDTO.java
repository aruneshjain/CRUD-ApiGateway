package com.first.order.DTO;

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
