package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.order.ReadCartBookResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cartAdaptor", url = "http://133.186.153.195:8085/users")
public interface CartAdaptor {

    @GetMapping("/{userId}/cart-books")
    List<ReadCartBookResponse> getCartBooks(@PathVariable Long userId);
}
