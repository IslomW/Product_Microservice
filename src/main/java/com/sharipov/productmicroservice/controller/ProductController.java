package com.sharipov.productmicroservice.controller;


import com.sharipov.productmicroservice.dto.ProductDTO;
import com.sharipov.productmicroservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO){
       String productId = productService.createProduct(productDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}
