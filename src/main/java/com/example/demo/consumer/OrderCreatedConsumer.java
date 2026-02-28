package com.example.demo.consumer;

import com.example.demo.consumer.dto.Order;
import com.example.demo.dto.LockProductDTO;
import com.example.demo.dto.LockProductItemDTO;
import com.example.demo.event.ProductLockedEvent;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedConsumer {
    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    @KafkaListener(topics = "order_created")
    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 2000, multiplier = 2.0),
            exclude = {NullPointerException.class, IllegalArgumentException.class}
    )
    public void handleOrderCreatedEvent(String orderString) throws JsonProcessingException {
        Order order = objectMapper.readValue(orderString, Order.class);
        log.info("Receive order message {}", order);

        List<LockProductItemDTO> lockProductItems = new ArrayList<>();
        order.getOrderItems().forEach(orderItem -> {
            LockProductItemDTO lockProductItem = new LockProductItemDTO(orderItem.getProductId(), orderItem.getQuantity());
            lockProductItems.add(lockProductItem);
        });
        LockProductDTO lockProductDTO = new LockProductDTO(lockProductItems);
        productService.lock(lockProductDTO);
        ProductLockedEvent productLockedEvent = new ProductLockedEvent(order.getId());
        kafkaTemplate.send("product_locked", productLockedEvent);
    }
}
