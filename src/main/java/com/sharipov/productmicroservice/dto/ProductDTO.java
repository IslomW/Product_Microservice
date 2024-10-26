package com.sharipov.productmicroservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private String title;
    private BigDecimal price;
    private Integer quantity;

}
