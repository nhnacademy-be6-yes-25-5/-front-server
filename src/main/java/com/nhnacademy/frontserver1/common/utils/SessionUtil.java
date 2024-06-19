package com.nhnacademy.frontserver1.common.utils;

import java.util.List;

public class SessionUtil {

    public static List<Long> getLongListFromSession(Object object) {
        List<Long> list = null;
        if (object instanceof List<?>) {
            list = ((List<?>) object).stream()
                .filter(Long.class::isInstance)
                .map(e -> (Long) e)
                .toList();
        }
        return list;
    }

    public static List<Integer> getIntegerListFromSession(Object object) {
        List<Integer> list = null;
        if (object instanceof List<?>) {
            list = ((List<?>) object).stream()
                .filter(Integer.class::isInstance)
                .map(e -> (Integer) e)
                .toList();
        }
        return list;
    }

}
