package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.TagService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.TagAdapter;
import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateTagRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateTagRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.TagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/admin/tag")
    public String adminTag(Model model, Pageable pageable) {

        Page<TagResponse> tagList = tagService.findAllTags(pageable);
        int nowPage = tagList.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, tagList.getTotalPages());

        model.addAttribute("tagList", tagList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admin/tag/admin-tag";
    }

    @GetMapping("/admin/tag/{tagId}/delete")
    public String deleteTag(@PathVariable Long tagId) {

        tagService.deleteTag(tagId);

        return "redirect:/admin/tag";
    }

    @PostMapping("/admin/tag")
    public String createTag(@ModelAttribute CreateTagRequest createTagRequest) {

        tagService.createTag(createTagRequest);

        return "redirect:/admin/tag";
    }

    @PostMapping("/admin/tag/update")
    public String updateTag(@ModelAttribute UpdateTagRequest updateTagRequest) {

        tagService.updateTag(updateTagRequest);

        return "redirect:/admin/tag";
    }
}
