package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.TagService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.TagAdapter;
import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateTagRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateTagRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.TagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagAdapter tagAdapter;

    @Override
    public TagResponse createTag(CreateTagRequest createTagRequest) {
        return tagAdapter.createTag(createTagRequest);
    }

    @Override
    public TagResponse findTag(Long tagId) {
        return null;
    }

    @Override
    public List<TagResponse> findAllTags() {
        return tagAdapter.findAllTags();
    }

    @Override
    public TagResponse updateTag(UpdateTagRequest updateTagRequest) {
        return tagAdapter.updateTag(updateTagRequest);
    }

    @Override
    public void deleteTag(Long tagId) {
        tagAdapter.deleteTagById(tagId);
    }
}
