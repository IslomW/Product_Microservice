package com.sharipov.productmicroservice.service.impl;

import com.sharipov.productmicroservice.dto.ProductDTO;
import com.sharipov.productmicroservice.event.ProductCreateEvent;
import com.sharipov.productmicroservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final KafkaTemplate<String, ProductCreateEvent> kafkaTemplate;
    @Override
    public String createProduct(ProductDTO productDTO) {
        //ToDO save  DB
        String productId = UUID.randomUUID().toString();

        ProductCreateEvent productCreateEvent = new ProductCreateEvent();
        productCreateEvent.setProductId(productId);
        productCreateEvent.setTitle(productDTO.getTitle());
        productCreateEvent.setPrice(productDTO.getPrice());
        productCreateEvent.setQuantity(productDTO.getQuantity());


//        //NONAsync
//        kafkaTemplate.send("product-created-events-topic", productId, productCreateEvent);


        //Async
        CompletableFuture<SendResult<String, ProductCreateEvent>> future = kafkaTemplate
                .send("product-created-events-topic", productId, productCreateEvent);

        future.whenComplete((result, exception) -> {
            if (exception != null){
                log.error("Failed to send message: {}", exception.getMessage());
            }else {
                log.info("Message send successfully: {}", result.getProducerRecord());
            }
        });

        //if we add future.join(); he waiting.

        log.info("Return: {}", productId);


//        Sync
//        SendResult<String , ProductCreateEvent> result = kafkaTemplate
//                .send("product-created-events-topic", productId, productCreateEvent).get();


        return productId;
    }
}
