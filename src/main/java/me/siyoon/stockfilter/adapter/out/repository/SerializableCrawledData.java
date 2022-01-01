package me.siyoon.stockfilter.adapter.out.repository;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import org.jsoup.Jsoup;

@Builder
@AllArgsConstructor
@ToString
public class SerializableCrawledData implements Serializable {

    public final String stockCode;
    public final String naverMainPage;
    public final String fnGuideMainPage;

    public static SerializableCrawledData from(CrawledData crawledData) {
        return SerializableCrawledData.builder()
                               .stockCode(crawledData.stockCode)
                               .naverMainPage(crawledData.naverMainPage.outerHtml())
                               .fnGuideMainPage(crawledData.fnGuideMainPage.outerHtml())
                               .build();
    }

    public CrawledData toCrawledData() {
        return CrawledData.builder()
                          .stockCode(stockCode)
                          .naverMainPage(Jsoup.parse(naverMainPage))
                          .fnGuideMainPage(Jsoup.parse(fnGuideMainPage))
                          .build();
    }
}
