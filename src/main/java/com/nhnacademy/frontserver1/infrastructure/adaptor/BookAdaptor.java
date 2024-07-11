package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateBookQuantityRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.BookCouponResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bookAdaptor", url = "${eureka.gateway}/books", configuration = FeignClientConfig.class)
public interface BookAdaptor {

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

    @GetMapping("/category/{categoryId}/page")
    Page<BookResponse> getBookByCategory(@PathVariable Long categoryId, Pageable pageable, @RequestParam String sortString);

    @GetMapping("/search")
    List<BookCouponResponseDTO> findBooksByName(@RequestParam("query") String query);

    @GetMapping("/categories")
    List<CategoryResponse> findAllCategories();

    @GetMapping("/categories/book/{bookId}")
    List<Long> getCategoryIdsByBookId(@PathVariable("bookId") Long bookId);

    @GetMapping("/{bookId}/addHitsCount")
    void addHitsCount(@PathVariable("bookId") Long bookId);
}