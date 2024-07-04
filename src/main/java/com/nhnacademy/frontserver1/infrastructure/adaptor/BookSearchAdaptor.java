package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.request.book.BookSearchRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookIndexResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name = "bookSearchAdapter", url = "${eureka.gateway}/books" , configuration = FeignClientConfig.class)
public interface BookSearchAdaptor {

    @GetMapping("/searchAll")
    Page<BookIndexResponse> searchAll(@RequestBody BookSearchRequest request);

    @GetMapping("/searchByName")
    Page<BookIndexResponse> searchByName(@RequestBody BookSearchRequest request);

    @GetMapping("/searchByDescription")
    Page<BookIndexResponse> searchByDescription(@RequestBody BookSearchRequest request);

    @GetMapping("/searchByTagName")
    Page<BookIndexResponse> searchByTagName(@RequestBody BookSearchRequest request);

    @GetMapping("/searchByAuthorName")
    Page<BookIndexResponse> searchByAuthorName(@RequestBody BookSearchRequest request);
}
