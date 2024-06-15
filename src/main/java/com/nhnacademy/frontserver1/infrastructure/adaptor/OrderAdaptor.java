package com.nhnacademy.frontserver1.infrastructure.adaptor;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "orderAdaptor", url = "http://localhost:8081/orders")
public interface OrderAdaptor {

}
