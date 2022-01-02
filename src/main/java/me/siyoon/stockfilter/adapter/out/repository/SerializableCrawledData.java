package me.siyoon.stockfilter.adapter.out.repository;

import java.io.Serializable;
import lombok.Builder;
import lombok.ToString;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.NaverFinancialSummaryPage;
import org.jsoup.Jsoup;

@ToString
public class SerializableCrawledData implements Serializable {

    public final String stockCode;
    public final String naverMainPage;
    public final String fnGuideMainPage;
    public final String annualFinancialSummaryPage;
    public final String quarterFinancialSummaryPage;

    @Builder
    public SerializableCrawledData(String stockCode, String naverMainPage,
                                   String fnGuideMainPage,
                                   NaverFinancialSummaryPage naverFinancialSummaryPage) {
        this.stockCode = stockCode;
        this.naverMainPage = naverMainPage;
        this.fnGuideMainPage = fnGuideMainPage;
        this.annualFinancialSummaryPage = naverFinancialSummaryPage.annualFinancialSummaryPage.html();
        this.quarterFinancialSummaryPage = naverFinancialSummaryPage.quarterFinancialSummaryPage.html();
    }

    public static SerializableCrawledData from(CrawledData crawledData) {
        return SerializableCrawledData.builder()
                                      .stockCode(crawledData.stockCode)
                                      .naverMainPage(crawledData.naverMainPage().document().html())
                                      .fnGuideMainPage(crawledData.fnGuideMainPage.html())
                                      .naverFinancialSummaryPage(crawledData.naverFinancialSummaryPage)
                                      .build();
    }

    public CrawledData toCrawledData() {
        return CrawledData.builder()
                          .stockCode(stockCode)
                          .naverMainPage(Jsoup.parse(naverMainPage))
                          .fnGuideMainPage(Jsoup.parse(fnGuideMainPage))
                          .annualFinancialSummaryPage(Jsoup.parse(annualFinancialSummaryPage))
                          .quarterFinancialSummaryPage(Jsoup.parse(quarterFinancialSummaryPage))
                          .build();
    }
}
