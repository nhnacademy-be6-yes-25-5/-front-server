package com.nhnacademy.frontserver1.application.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.frontserver1.application.service.BookService;
import com.nhnacademy.frontserver1.common.exception.ApplicationException;
import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;
import com.nhnacademy.frontserver1.infrastructure.adaptor.BookAdapter;
import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookAPIResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceimpl implements BookService {

    private final String clientSecret = "mmnE38HTvttvM422Rk00";

    private final String clientId = "4HfHyIX7Xd";

    private final BookAdapter bookAdapter;

    @Override
    public List<BookAPIResponse> searchBooks(String keyword) {

        List<BookAPIResponse> bookList = new ArrayList<>();

        try {
            String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/book.json?query=" + encodedKeyword;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.toString());
            JsonNode itemsNode = rootNode.path("items");

            for (JsonNode itemNode : itemsNode) {
                BookAPIResponse bookDto = BookAPIResponse.builder()
                        .title(itemNode.path("title").asText())
                        .author(itemNode.path("author").asText())
                        .imageURL(itemNode.path("image").asText())
                        .isbn(itemNode.path("isbn").asText())
                        .publisher(itemNode.path("publisher").asText())
                        .price(Integer.parseInt(itemNode.path("discount").asText()))
                        .description(itemNode.path("description").asText())
                        .publishDate(LocalDate.parse(itemNode.path("pubdate").asText(), DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay())
                        .build();
                bookList.add(bookDto);
            }
        } catch (Exception e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("책 검색 중 알수없는 오류가 발생했습니다.", 500, LocalDateTime.now()));
        }

        return bookList;
    }

    @Override
    public BookResponse createBook(CreateBookRequest createBookRequest, List<Long> categoryIdList, List<Long> tagIdList) {
        return bookAdapter.createBook(createBookRequest, categoryIdList, tagIdList);
    }

    @Override
    public Page<BookResponse> findAllBooks(Pageable pageable) {
        return bookAdapter.findAllBooks(pageable);
    }

    @Override
    public void deleteBook(Long id) {
        bookAdapter.deleteBook(id);
    }

    @Override
    public BookResponse updateBook(UpdateBookRequest updateBookRequest, List<Long> categoryIdList, List<Long> tagIdList) {
        return bookAdapter.updateBook(updateBookRequest, categoryIdList, tagIdList);
    }
}
