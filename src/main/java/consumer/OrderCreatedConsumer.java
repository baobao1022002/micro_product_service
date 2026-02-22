package consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import consumer.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCreatedConsumer {

    @KafkaListener(topics = "order_created")
    public void handleOrderCreatedEvent(String orderString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = objectMapper.readValue(orderString, Order.class);
        log.info("Receive order message {}", order);

    }

//    @KafkaListener(topics = "order_created")
//    public void handleOrderCreatedEvent(Order order) throws JsonProcessingException {
//        log.info("Receive order message {}", order);
//
//    }

}
