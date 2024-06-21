package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadShippingPolicyResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadTakeoutResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "policyAdaptor", url = "http://133.186.153.195:8085/policies")
public interface PolicyAdaptor {

    // todo. 추후 페이징이 아닌 리스트로 가져오게 바꿀 예정입니다.
    @GetMapping("/shipping")
    Page<ReadShippingPolicyResponse> findAllDeliveryPolicy(Pageable pageable);

    @GetMapping("/shipping/free")
    ReadShippingPolicyResponse findFreePolicy();

    @GetMapping("/takeout")
    List<ReadTakeoutResponse> findAllTakeoutPolicy();
}
