package me.siyoon.stockfilter.adapter.out.util;

import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberExtractor {

    private static final Set<String> IGNORED_TEXT = Set.of("완전잠식", "-", "N/A");

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
        return text == null || text.isEmpty() || IGNORED_TEXT.contains(text);
    }
}
