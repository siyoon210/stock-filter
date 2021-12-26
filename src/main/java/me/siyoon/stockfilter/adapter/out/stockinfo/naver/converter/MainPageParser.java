package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.siyoon.stockfilter.exception.StockInfoParseException;
import org.jsoup.nodes.Document;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MainPageParser {

    public static String companyName(Document document) {
        try {
            return document.getElementById("middle")
                           .getElementsByTag("h2").get(0)
                           .getElementsByTag("a").get(0).text();
        } catch (Exception e) {
            throw new StockInfoParseException("회사이름 파싱 실패");
        }
    }
}
