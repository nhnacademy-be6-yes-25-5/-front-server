package com.nhnacademy.frontserver1.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class TokenExtractorAspect {

    @AfterReturning(
            pointcut = "execution(* com.nhnacademy.frontserver1.infrastructure.adaptor.*.*(..))",
            returning = "result"
    )
    public void extractTokenFromResponse(Object result) {
        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;

            // 토큰 갱신시의 응답 헤더에서 토큰 가져오기
            HttpHeaders headers = responseEntity.getHeaders();
            String accessToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
            String refreshToken = headers.getFirst("Refresh-Token");

            // DispatcherServletContext에 토큰 저장
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes) {
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                request.setAttribute("AccessToken", accessToken);
                request.setAttribute("RefreshToken", refreshToken);
            }
        }
    }
}