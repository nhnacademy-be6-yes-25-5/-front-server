package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateTagRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateTagRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.TagResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "tagAdapter", url = "http://localhost:8050/tags")
public interface TagAdapter {

    @GetMapping
    List<TagResponse> findAllTags();

    @GetMapping("/{tagId}")
    TagResponse findTagById(@PathVariable("tagId") Long tagId);

    @PostMapping
    TagResponse createTag(CreateTagRequest request);

    @PutMapping
    TagResponse updateTag(UpdateTagRequest request);

    @DeleteMapping
    void deleteTagById(@PathVariable("tagId") Long tagId);
}
