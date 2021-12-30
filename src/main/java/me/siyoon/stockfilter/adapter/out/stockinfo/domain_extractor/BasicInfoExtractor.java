package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.siyoon.stockfilter.exception.StockInfoParseException;
import org.jsoup.nodes.Document;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BasicInfoExtractor {

    public static String companyName(Document naverMainPage) {
        try {
            return naverMainPage.getElementById("middle")
                                .getElementsByTag("h2").get(0)
                                .getElementsByTag("a").get(0).text();
        } catch (Exception e) {
            throw new StockInfoParseException("회사이름 파싱 실패");
        }
    }
}
