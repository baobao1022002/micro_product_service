package com.example.demo;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@EnableKafka
public class ShoppingProjectApplication {

    @PostConstruct
    public void init() {
        log.info("OrderCreatedConsumer started");
    }

    public static void main(String[] args) {
        SpringApplication.run(ShoppingProjectApplication.class, args);
    }

}


