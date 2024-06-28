package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookAPIResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.UploadImageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    List<BookAPIResponse> searchBooks(String keyword);

    BookResponse createBook(CreateBookRequest createBookRequest, List<Long> categoryIdList, List<Long> tagIdList);

    Page<BookResponse> findAllBooks(Pageable pageable);

    void deleteBook(Long id);

    BookResponse updateBook(UpdateBookRequest updateBookRequest, List<Long> categoryIdList, List<Long> tagIdList);
}
