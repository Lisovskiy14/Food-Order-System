package org.example.foodordersystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class FoodOrderSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodOrderSystemApplication.class, args);
    }

}
