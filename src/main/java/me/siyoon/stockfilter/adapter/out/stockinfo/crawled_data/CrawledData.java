package me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data;

import lombok.Builder;
import lombok.ToString;
import org.jsoup.nodes.Document;

@ToString
public class CrawledData {

    public static final CrawledData EMPTY = CrawledData.builder().build();

    public final String stockCode;
    public final NaverMainPage naverMainPage;
    public final NaverFinSummaryPage naverFinSummaryPage;

    public NaverMainPage naverMainPage() {
        return naverMainPage;
    }

    @Builder
    public CrawledData(String stockCode,
                       Document naverMainPage,
                       Document naverAnnualFinSummaryPage,
                       Document naverQuarterFinSummaryPage) {
        this.stockCode = stockCode;
        this.naverMainPage = NaverMainPage.from(naverMainPage);
        this.naverFinSummaryPage = NaverFinSummaryPage.of(naverAnnualFinSummaryPage,
                                                          naverQuarterFinSummaryPage);
    }
}
