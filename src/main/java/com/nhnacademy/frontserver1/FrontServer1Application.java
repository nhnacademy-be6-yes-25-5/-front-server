package com.nhnacademy.frontserver1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
public class FrontServer1Application {

    public static void main(String[] args) {
        SpringApplication.run(FrontServer1Application.class, args);
    }

}
