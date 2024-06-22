package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateTagRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateTagRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.TagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    TagResponse createTag(CreateTagRequest createTagRequest);
    TagResponse findTag(Long tagId);
    Page<TagResponse> findAllTags(Pageable pageable);
    List<TagResponse> findAllTags();
    TagResponse updateTag(UpdateTagRequest updateTagRequest);
    void deleteTag(Long tagId);

}
