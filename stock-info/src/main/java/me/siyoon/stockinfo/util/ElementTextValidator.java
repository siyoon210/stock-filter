package me.siyoon.stockinfo.util;

import org.jsoup.nodes.Element;

public class ElementTextValidator {
    public static void validateElement(Element element, String expectedValue) {
        String actualText = element.getElementsByTag("th").get(0).text();
        if (!expectedValue.equals(actualText)) {
            throw new IllegalArgumentException(expectedValue + "이 아닌 거 같은데?!");
        }
    }
}
