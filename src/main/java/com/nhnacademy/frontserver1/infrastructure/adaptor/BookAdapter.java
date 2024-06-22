package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateBookQuantityRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bookAdapter", url = "http://localhost:8085/books")
public interface BookAdapter {

    @GetMapping("/page")
    Page<BookResponse> findAllBooks(Pageable pageable);

    @GetMapping
    List<BookResponse> findAllBooks();

    @GetMapping("/{bookId}")
    BookResponse findBookById(@PathVariable("bookId") Long bookId);

    @PostMapping
    BookResponse createBook(@RequestBody CreateBookRequest request, @RequestParam(value = "categoryIdList") List<Long> categoryIdList, @RequestParam(value = "tagIdList", required = false) List<Long> tagIdList);

    @PutMapping
    BookResponse updateBook(@RequestBody UpdateBookRequest request, @RequestParam(value = "categoryIdList") List<Long> categoryIdList, @RequestParam(value = "tagIdList", required = false) List<Long> tagIdList);

    @PatchMapping
    BookResponse updateBookQuantity(@RequestBody UpdateBookQuantityRequest request);

    @DeleteMapping("/{bookId}")
    ResponseEntity<Void> deleteBook(@PathVariable("bookId") Long bookId);
}
