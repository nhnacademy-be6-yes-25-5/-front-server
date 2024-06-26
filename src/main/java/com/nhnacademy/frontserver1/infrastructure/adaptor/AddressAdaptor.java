package com.nhnacademy.frontserver1.infrastructure.adaptor;


import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderUserAddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "addressAdaptor", url = "${eureka.gateway}/users", configuration = FeignClientConfig.class)
public interface AddressAdaptor {

    @GetMapping("/addresses")
    Page<ReadOrderUserAddressResponse> getUserAddresses(Pageable pageable);
}
