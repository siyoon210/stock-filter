package me.siyoon.stockinfo.util;

import org.jsoup.nodes.Element;

public class NumberExtractor {
    public static double getNumberValue(Element element, int index) throws NumberFormatException {
        String value = element.getElementsByTag("td")
                .get(index).text()
                .replace(",", "");
        return Double.parseDouble(value);
    }
}
