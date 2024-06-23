package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateLikesRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateLikesRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.LikesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "likesAdapter", url = "http://localhost:8050/likes")
public interface LikesAdapter {

    @GetMapping("/users/{userId}")
    LikesResponse findLikesByUserId(@PathVariable("userId") String userId);

    @GetMapping("/books/{bookId}")
    LikesResponse findLikesByBookId(@PathVariable("bookId") String bookId);

    @PutMapping
    LikesResponse updateLikes(@RequestBody UpdateLikesRequest request);

    @PostMapping
    LikesResponse createLikes(@RequestBody CreateLikesRequest request);
}
