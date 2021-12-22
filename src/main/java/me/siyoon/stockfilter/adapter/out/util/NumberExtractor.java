package me.siyoon.stockfilter.adapter.out.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberExtractor {

    public static Double doubleValue(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        return Double.valueOf(text.replace(",", ""));
    }
}
