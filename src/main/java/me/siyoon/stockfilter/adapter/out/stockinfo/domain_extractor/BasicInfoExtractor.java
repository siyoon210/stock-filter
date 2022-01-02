package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import org.apache.logging.log4j.util.Strings;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BasicInfoExtractor {

    public static String companyName(CrawledData crawledData) {
        try {
            return crawledData.naverMainPage().document()
                              .getElementById("middle")
                              .getElementsByTag("h2").get(0)
                              .getElementsByTag("a").get(0).text();
        } catch (Exception e) {
            log.warn("companyName 파싱 실패. stockCode = {}", crawledData.stockCode);
            return Strings.EMPTY;
        }
    }
}
