package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.GradeService;
import com.nhnacademy.frontserver1.presentation.dto.response.userGrade.GradeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
//@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/grade")
    public String getUserGrade(@PathVariable Long userId, Model model) {
        GradeResponse userGrade = gradeService.getUserGradeByUserId(userId);
        model.addAttribute("userGrade", userGrade);
        return "mypage/mypage-grade";
    }

//    @GetMapping("/user/grade")
//    public String getUserGrade(@RequestParam Long userId, Model model) {
//        GradeResponse gradeResponse = userGradeService.getUserGradeByUserId(userId);
//        model.addAttribute("grade", gradeResponse);
//        return "mypage/mypage-grade"; // grade.html 템플릿을 가리킵니다.
//    }

//    @GetMapping("/user/grade/{userId}")
//    public String getUserGrade(@RequestParam Long userId, Model model) {
//        GradeResponse userGrade = userGradeService.getUserGradeByUserId(userId);
//        model.addAttribute("userGrade", userGrade);
//        return "mypage/mypage-grade";
//    }

//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
//        ModelAndView mav = new ModelAndView("error/grade-fail");
//        mav.addObject("message", "Required parameter 'userId' is missing");
//        return mav;
//    }
}