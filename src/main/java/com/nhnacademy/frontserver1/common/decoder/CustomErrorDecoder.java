package com.nhnacademy.frontserver1.common.decoder;

import com.nhnacademy.frontserver1.common.exception.FeignClientException;
import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String responseBody = null;

        try {
            if (response.body() != null) {
                responseBody = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            log.error("응답 본문을 읽는 중 에러 발생: methodKey={}, status={}, 에러 메시지={}", methodKey, response.status(), e.getMessage(), e);
        }

        return handleException(response, responseBody);
    }

    private Exception handleException(Response response, String responseBody) {
        int status = response.status();
        switch (status) {
            case 400:
                log.error("클라이언트 요청에서 에러가 발생하였습니다. 상태 코드: 400, 응답 본문: {}", responseBody);
                break;
            case 500:
                log.error("서버에서 에러가 발생하였습니다. 상태 코드: 500, 응답 본문: {}", responseBody);
                break;
            default:
                log.error("알 수 없는 에러가 발생하였습니다. 상태 코드: {}, 응답 본문: {}", status, responseBody);
        }

        return throwFeignClientException(response, responseBody);
    }

    private FeignClientException throwFeignClientException(Response response, String responseBody) {
        return new FeignClientException(
                ErrorStatus.toErrorStatus(responseBody, response.status(), LocalDateTime.now()));
    }
}