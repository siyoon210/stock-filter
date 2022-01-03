package me.siyoon.stockfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class StockFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockFilterApplication.class, args);
    }

}
