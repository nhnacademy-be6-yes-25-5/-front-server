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
import com.nhnacademy.frontserver1.common.exception.AccessDeniedException;
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
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String servletPath = request.getServletPath();
        String feignPath = template.path();

        if (isServletPathAndFeignExclude(servletPath, feignPath)) {
            return;
        }

        String accessToken = TokenContext.getAccessToken();
        String refreshToken = TokenContext.getRefreshToken();

        boolean isTokensEmpty = accessToken.isBlank() || refreshToken.isBlank();

        if (isTokensEmptyAndIsServletPathExclude(isTokensEmpty, servletPath)) {
            return;
        }

        if (path.startsWith("/detail") && request.getMethod().equalsIgnoreCase("GET")) {
            return;
        }

        if (isTokensEmptyAndIsServletPathExcludeWithHttpMethod(isTokensEmpty, servletPath, template)) {
            return;
        }

        if (!isTokensEmpty) {
            if (servletPath.equals("/auth/login")) {
                throw new AccessDeniedException(ErrorStatus.toErrorStatus(
                        "불가능한 접근입니다.", 409, LocalDateTime.now()
                ));
            }
            template.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            log.debug("Adding Authorization header: Bearer {}", accessToken);
            template.header("Refresh-Token", refreshToken);
            log.debug("Adding RefreshToken header: {}", refreshToken);
        } else {
            if(servletPath.equals("/likesList")) {
                throw new LikesNotLoginException(ErrorStatus.toErrorStatus("로그인 시 좋아요 표시한 도서를 볼 수 있습니다.",401, LocalDateTime.now()));
            }

            if(servletPath.matches("/likesClick/\\d+")) {
                throw new LikesNotLoginException(ErrorStatus.toErrorStatus("로그인 시 좋아요를 표시할 수 있습니다.", 401, LocalDateTime.now()));
            }

            log.warn("Authorization token is missing in the cookies.");
            throw new TokenCookieMissingException();
        }
    }

    /**
     * 서블릿 경로이면서, 토큰이 필요한 있는 요청 중 Http 메소드와 토큰이 없는 상황을 고려해야 할 경우 경로를 추가해주시면 됩니다.
     * ex) 회원, 비회원(비회원 중에서도 토큰이 없는 비회원) 구분이 필요할 경우
     * @param servletPath 서블릿 요청 경로입니다.
     * @param isTokensEmpty 엑세스, 리프레시 토큰 둘다 비어있으면 true, 하나라도 있으면 false
     * */
    private boolean isTokensEmptyAndIsServletPathExcludeWithHttpMethod(boolean isTokensEmpty,
        String servletPath, RequestTemplate template) {
        return isTokensEmpty && (servletPath.startsWith("/users/cart-books") || template.method().equalsIgnoreCase("POST"));
    }

    /**
     * 서블릿 경로이면서, 토큰이 필요한 있는 요청 중 토큰이 없는 상황을 고려해야 할 경우 경로를 추가해주시면 됩니다.
     * ex) 회원, 비회원(비회원 중에서도 토큰이 없는 비회원) 구분이 필요할 경우
     * @param servletPath 서블릿 경로입니다.
     * @param isTokensEmpty 엑세스, 리프레시 토큰 둘다 비어있으면 true, 하나라도 있으면 false
     * */
    private boolean isTokensEmptyAndIsServletPathExclude(boolean isTokensEmpty, String servletPath) {
        return isTokensEmpty && (servletPath.matches(".*/orders/.*/delivery.*") || servletPath.startsWith("/users/cart-books")
            || servletPath.startsWith("/detail") || servletPath.startsWith("/books") || servletPath.matches("/coupons") || servletPath.startsWith("/reviews/books"));
    }

    /**
     * 서블릿 경로이거나 feign경로이면서, 토큰이 필요없는 요청일 경우 경로를 추가해주시면 됩니다.
     *
     * @param servletPath 서블릿 경로입니다.
     * @param feignPath feign 경로입니다.
     */
    private boolean isServletPathAndFeignExclude(String servletPath, String feignPath) {
        return (servletPath.equals("/") || servletPath.startsWith("/orders/none") || servletPath.startsWith("/category") || servletPath.startsWith("/search")
            || servletPath.startsWith("/sign-up") || servletPath.startsWith("/books") || servletPath.matches("/coupons") || servletPath.startsWith("/check-email")
            || servletPath.startsWith("/auth/dormant")|| servletPath.startsWith("/users/sign-up") || servletPath.equals("/callback") || servletPath.startsWith("/users/find/password"))
            && !feignPath.startsWith("/cart-books");
    }

}
