package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookAPIResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookResponse;

import java.util.List;

public interface BookService {

    List<BookAPIResponse> searchBooks(String keyword);

    BookResponse createBook(CreateBookRequest createBookRequest, List<Long> categoryIdList, List<Long> tagIdList);

    List<BookResponse> findAllBooks();

    void deleteBook(Long id);
}
