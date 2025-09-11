package com.example.test_clever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan
public class TestCleverApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestCleverApplication.class, args);
    }
}








