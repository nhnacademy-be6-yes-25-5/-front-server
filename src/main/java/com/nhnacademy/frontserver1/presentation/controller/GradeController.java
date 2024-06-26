package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.GradeService;
import com.nhnacademy.frontserver1.presentation.dto.response.userGrade.GradeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class GradeController {

    private final GradeService userGradeService;

    @GetMapping("/user/grade")
    public String getUserGrade(@RequestParam("userId") Long userId, Model model) {
        GradeResponse userGrade = userGradeService.getUserGradeByUserId(userId);
        model.addAttribute("userGrade", userGrade);
        return "user/grade";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        ModelAndView mav = new ModelAndView("error/grade-fail");
        mav.addObject("message", "Required parameter 'userId' is missing");
        return mav;
    }
}