package com.nhnacademy.frontserver1.common.provider;

import com.nhnacademy.frontserver1.common.exception.TokenCookieMissingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class CookieTokenProvider {

    /**
     * HttpServletRequest의 쿠키에서 'Authorization' 토큰을 추출합니다.
     *
     * @param request 현재 HTTP 요청
     * @return 쿠키에서 추출한 토큰. 토큰이 없으면 빈 Optional을 반환합니다.
     */
    public Optional<String> getTokenFromCookie(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {

                    return Optional.of(cookie.getValue());
                }
            }
            throw new TokenCookieMissingException();
        }

        return Optional.empty();
    }

}