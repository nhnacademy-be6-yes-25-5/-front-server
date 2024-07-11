package com.nhnacademy.frontserver1.common.interceptor;

import com.nhnacademy.frontserver1.common.exception.LikesNotLoginException;
import com.nhnacademy.frontserver1.common.exception.TokenCookieMissingException;
import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;
import com.nhnacademy.frontserver1.common.provider.CookieTokenProvider;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import com.nhnacademy.frontserver1.common.context.TokenContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;
import com.nhnacademy.frontserver1.common.exception.AccessDeniedException;
import java.time.LocalDateTime;

import java.time.LocalDateTime;

/**
 * JWT 인증을 위한 Feign 요청 인터셉터입니다.
 * 이 인터셉터는 쿠키에서 JWT 토큰을 추출하여 Feign 요청의 Authorization 헤더에 추가합니다.
 * Authorization 쿠키(토큰이 담겨있는 쿠키)가 없는 경우 NoTokenCookieException을 던져 로그인 페이지로 리디렉션합니다.
 */
@Slf4j
@RequiredArgsConstructor
public class FeignJwtTokenInterceptor implements RequestInterceptor {

    private final CookieTokenProvider cookieTokenProvider;

    /**
     * Feign 요청에 JWT 토큰을 추가합니다.
     * '/auth/login' 경로에 대한 요청은 인증이 필요하지 않으므로 토큰을 추가하지 않습니다.
     *
     * @param template Feign 요청 템플릿
     */
    @Override
    public void apply(RequestTemplate template) {
        String path = template.path();

        if (path.equals("/") || path.startsWith("/orders/none") || path.startsWith("/category") || path.startsWith("/search")
          || path.startsWith("/sign-up") || path.startsWith("/books") || path.matches("/coupons") || path.startsWith("/check-email")
                || path.startsWith("/auth/dormant")|| path.startsWith("/users/sign-up") || path.equals("/callback") || path.startsWith("/users/find/password")) {

            return;
        }

        String accessToken = TokenContext.getAccessToken();
        String refreshToken = TokenContext.getRefreshToken();

        boolean isTokensEmpty = accessToken.isBlank() || refreshToken.isBlank();

        if (isTokensEmpty && (path.matches(".*/orders/.*/delivery.*") || path.startsWith("/users/cart-books")
                || path.startsWith("/detail") || path.startsWith("/books") || path.matches("/coupons") || path.startsWith("/reviews/books"))) {
            return;
        }

        if (isTokensEmpty && (path.startsWith("/users/cart-books") || template.method().equalsIgnoreCase("POST"))) {
            return;
        }

        if (!isTokensEmpty) {
            if (path.equals("/auth/login")) {
                throw new AccessDeniedException(ErrorStatus.toErrorStatus(
                        "불가능한 접근입니다.", 409, LocalDateTime.now()
                ));
            }
            template.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            log.debug("Adding Authorization header: Bearer {}", accessToken);
            template.header("Refresh-Token", refreshToken);
            log.debug("Adding RefreshToken header: {}", refreshToken);
        } else {
            if(path.equals("/likesList")) {
                throw new LikesNotLoginException(ErrorStatus.toErrorStatus("로그인 시 좋아요 표시한 도서를 볼 수 있습니다.",401, LocalDateTime.now()));
            }

            if(path.matches("/likesClick/\\d+")) {
                throw new LikesNotLoginException(ErrorStatus.toErrorStatus("로그인 시 좋아요를 표시할 수 있습니다.", 401, LocalDateTime.now()));
            }

            log.warn("Authorization token is missing in the cookies.");
            throw new TokenCookieMissingException();
        }
    }

}
