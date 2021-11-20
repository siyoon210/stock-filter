package me.siyoon.stockinfo.util;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LastYearIndexExtractor {
    public static int getLastYearIndex(Element element) {
        try {
            Elements td = element.getElementsByTag("td");
            if (td.get(3).text().isBlank() || td.get(3).text().trim().equals("&nbsp;")) {
                return 2;
            }
            return 3;
        } catch (Exception e) {
            return -1;
        }
    }
}
