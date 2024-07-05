package com.nhnacademy.frontserver1.common.jwt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Feign 요청에 대한 토큰 주입을 건너뛰도록 하는 어노테이션입니다.
 * 이 어노테이션이 붙은 메서드는 AOP에 의해 토큰 추가 처리가 수행되지 않습니다.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SkipTokenInjection {
}