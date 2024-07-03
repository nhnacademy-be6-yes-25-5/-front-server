package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.infrastructure.adaptor.LikesAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class LikesController {

    private final LikesAdapter likesAdapter;

    @GetMapping("/likes/{bookId}")
    public String likesClick(@PathVariable Long bookId) {

        likesAdapter.click(bookId);

        return "redirect:/detail/" + bookId;
    }
}
