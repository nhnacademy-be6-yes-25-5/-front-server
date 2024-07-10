package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateLikesRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateLikesRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.LikesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "likesAdapter", url = "${eureka.gateway}/books/likes")
public interface LikesAdapter {

    @GetMapping("/users")
    List<LikesResponse> findLikesByUserId();

    @GetMapping("/books/{bookId}")
    LikesResponse findLikesByBookId(@PathVariable("bookId") String bookId);

    @PostMapping("{bookId}")
    LikesResponse click(@PathVariable Long bookId);

    @GetMapping("/{bookId}")
    LikesResponse findByBookIdAndUserId(@PathVariable Long bookId);

    @GetMapping("/{bookId}/exist")
    Boolean exist(@PathVariable Long bookId);
}
