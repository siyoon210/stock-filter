package me.siyoon.stockfilter.adapter.out.stockinfo.naver;

import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.exception.StockInfoParseException;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class NaverStockInfoParser {

    public String companyName(Document document) {
        try {
            return document.getElementById("middle")
                           .getElementsByTag("h2").get(0)
                           .getElementsByTag("a").get(0).text();
        } catch (Exception e) {
            throw new StockInfoParseException("회사이름 파싱 실패");
        }
    }
}
