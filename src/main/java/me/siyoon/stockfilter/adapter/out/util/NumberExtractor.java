package me.siyoon.stockfilter.adapter.out.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberExtractor {

    public static Double doubleValue(String text) {
        if (isEmptyString(text)) {
            return null;
        }
        return Double.valueOf(text.replace(",", ""));
    }

    public static Long longValue(String text) {
        if (isEmptyString(text)) {
            return null;
        }
        return Long.valueOf(text.replace(",", ""));
    }

    private static boolean isEmptyString(String text) {
        return text == null || text.isEmpty() || text.equals(("-"));
    }
}
