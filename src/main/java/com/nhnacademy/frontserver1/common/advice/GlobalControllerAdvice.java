package com.nhnacademy.frontserver1.common.advice;

import com.nhnacademy.frontserver1.common.exception.*;
import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import com.nhnacademy.frontserver1.common.context.TokenContext;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalControllerAdvice {

    private final MessageSource messageSource;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @ExceptionHandler(FeignClientException.class)
    public ResponseEntity<ErrorStatus> handleFeignClientException(FeignClientException e, Model model) {
        ErrorStatus errorStatus = e.getErrorStatus();

        log.error("error :", e);

        return new ResponseEntity<>(errorStatus, errorStatus.toHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(AccessDeniedException e) {

        RedirectView redirectView = new RedirectView("/auth/error");
        redirectView.addStaticAttribute("cause", e.getErrorStatus().message());
        return new ModelAndView(redirectView);
    }



    @ExceptionHandler({RefreshTokenFailedException.class})
    public ModelAndView handleRefreshTokenFailedException(RefreshTokenFailedException e) {

        RedirectView redirectView = new RedirectView("/auth/error");
        redirectView.addStaticAttribute("cause", e.getErrorStatus().message());
        return new ModelAndView(redirectView);
    }

    @ExceptionHandler({ExpireRefreshJwtException.class})
    public ModelAndView handleRefreshTokenFailedException(ExpireRefreshJwtException e) {
        // 로그아웃 처리
        deleteCookie(response, "AccessToken");
        deleteCookie(response, "RefreshToken");
        TokenContext.clear();
        request.getSession().invalidate();

        // 로그아웃 후 리다이렉트
        RedirectView redirectView = new RedirectView("/auth/error");
        redirectView.addStaticAttribute("cause", "인증 정보가 만료되었습니다.");
        return new ModelAndView(redirectView);
    }

    private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @ExceptionHandler({TokenCookieMissingException.class})
    public ModelAndView handleTokenCookieMissingException(TokenCookieMissingException e) {

        RedirectView redirectView = new RedirectView("/auth/login");
        return new ModelAndView(redirectView);
    }

    @ExceptionHandler({UnauthorizedAccessException.class})
    public ModelAndView handleUnauthorizedAccessException(UnauthorizedAccessException e) {

        RedirectView redirectView = new RedirectView("/auth/error");
        redirectView.addStaticAttribute("cause", e.getErrorStatus().message());
        return new ModelAndView(redirectView);
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("cause", e.getMessage());
        return "error/auth-fail";
    }

    @ExceptionHandler(OrderWaitingException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderWaitingException e) {
        log.warn("OrderWaitingException 발생: ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(e.getMessage());
    }

    @ExceptionHandler(DormantAccountException.class)
    public String handleDormantAccountException(DormantAccountException e) {
        log.info("유저가 휴면 페이지로 리다이렉트됩니다.");

        return "redirect:/dormant";
    }

    @ExceptionHandler(ConnectionException.class)
    public ModelAndView handleCustomFeignException(ConnectionException e) {
        log.error("ConnectionException 발생: ", e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", e.getErrorStatus().message());
        mav.setViewName("error/server-fail");
        return mav;
    }

    @ExceptionHandler(LoginException.class)
    public String handleLoginException(LoginException e, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("errorMessage",
                messageSource.getMessage("login.fail", null, LocaleContextHolder.getLocale()));

        return "redirect:/auth/login";
    }
}
