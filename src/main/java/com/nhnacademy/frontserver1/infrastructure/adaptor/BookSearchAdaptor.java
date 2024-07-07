package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.request.book.BookSearchRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookIndexResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bookSearchAdapter", url = "${eureka.gateway}/books" , configuration = FeignClientConfig.class)
public interface BookSearchAdaptor {

    @GetMapping("/searchAll")
    Page<BookIndexResponse> searchAll(@RequestParam String keyword, Pageable pageable);

    @GetMapping("/searchByName")
    Page<BookIndexResponse> searchByName(@RequestParam String keyword, Pageable pageable);

    @GetMapping("/searchByDescription")
    Page<BookIndexResponse> searchByDescription(@RequestParam String keyword, Pageable pageable);

    @GetMapping("/searchByTagName")
    Page<BookIndexResponse> searchByTagName(@RequestParam String keyword, Pageable pageable);

    @GetMapping("/searchByAuthorName")
    Page<BookIndexResponse> searchByAuthorName(@RequestParam String keyword, Pageable pageable);

    @GetMapping("/searchByCategoryName")
    Page<BookIndexResponse> searchByCategoryName(@RequestParam String keyword, Pageable pageable);
}

