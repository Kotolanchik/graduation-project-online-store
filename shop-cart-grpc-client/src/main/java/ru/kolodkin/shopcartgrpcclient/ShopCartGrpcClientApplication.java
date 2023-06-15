package ru.kolodkin.shopcartgrpcclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ShopCartGrpcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopCartGrpcClientApplication.class, args);
    }

}
