package com.sharipov.productmicroservice.service.impl;

import com.sharipov.productmicroservice.dto.ProductDTO;
import com.sharipov.productmicroservice.event.ProductCreateEvent;
import com.sharipov.productmicroservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private KafkaTemplate<String, ProductCreateEvent> kafkaTemplate;
    @Override
    public String createProduct(ProductDTO productDTO) {
        //ToDO save  DB
        String productId = UUID.randomUUID().toString();

        ProductCreateEvent productCreateEvent = new ProductCreateEvent();
        productCreateEvent.setProductId(productId);
        productCreateEvent.setTitle(productDTO.getTitle());
        productCreateEvent.setPrice(productDTO.getPrice());
        productCreateEvent.setQuantity(productDTO.getQuantity());


        //Async
        kafkaTemplate.send("product-created-events-topic", productId, productCreateEvent);



        return productId;
    }
}
