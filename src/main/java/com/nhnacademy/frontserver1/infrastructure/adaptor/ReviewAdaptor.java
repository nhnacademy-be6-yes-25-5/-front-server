package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "reviewAdaptor", url = "${eureka.gateway}/reviews", configuration = FeignClientConfig.class)
public interface ReviewAdaptor {

    @PostMapping(consumes = "multipart/form-data")
    void createReview(@RequestPart("createReviewRequest") String createReviewRequestJson, @RequestPart(value = "images", required = false) List<MultipartFile> images);
}
