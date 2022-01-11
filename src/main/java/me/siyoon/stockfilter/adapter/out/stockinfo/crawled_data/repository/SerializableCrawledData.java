package me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.repository;

import java.io.Serializable;
import lombok.Builder;
import lombok.ToString;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.NaverFinSummaryPage;
import org.jsoup.Jsoup;

@ToString
public class SerializableCrawledData implements Serializable {

    public final String stockCode;
    public final String naverMainPage;
    public final String annualFinSummaryPage;
    public final String quarterFinSummaryPage;

    @Builder
    public SerializableCrawledData(String stockCode, String naverMainPage,
                                   NaverFinSummaryPage naverFinSummaryPage) {
        this.stockCode = stockCode;
        this.naverMainPage = naverMainPage;
        this.annualFinSummaryPage = naverFinSummaryPage.annualFinSummaryPage.html();
        this.quarterFinSummaryPage = naverFinSummaryPage.quarterFinSummaryPage.html();
    }

    public static SerializableCrawledData from(CrawledData crawledData) {
        return SerializableCrawledData.builder()
                                      .stockCode(crawledData.stockCode)
                                      .naverMainPage(crawledData.naverMainPage().document().html())
                                      .naverFinSummaryPage(crawledData.naverFinSummaryPage)
                                      .build();
    }

    public CrawledData toCrawledData() {
        return CrawledData.builder()
                          .stockCode(stockCode)
                          .naverMainPage(Jsoup.parse(naverMainPage))
                          .naverAnnualFinSummaryPage(Jsoup.parse(annualFinSummaryPage))
                          .naverQuarterFinSummaryPage(Jsoup.parse(quarterFinSummaryPage))
                          .build();
    }
}
