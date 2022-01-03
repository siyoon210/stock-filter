package me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data;

import lombok.Builder;
import lombok.ToString;
import org.jsoup.nodes.Document;

@ToString
public class CrawledData {

    public final String stockCode;
    public final NaverMainPage naverMainPage;
    public final Document fnGuideMainPage;
    public final NaverFinancialSummaryPage naverFinancialSummaryPage;

    public NaverMainPage naverMainPage() {
        return naverMainPage;
    }

    @Builder
    public CrawledData(String stockCode,
                       Document naverMainPage, Document fnGuideMainPage,
                       Document naverAnnualFinancialSummaryPage,
                       Document naverQuarterFinancialSummaryPage) {
        this.stockCode = stockCode;
        this.naverMainPage = NaverMainPage.from(naverMainPage);
        this.fnGuideMainPage = fnGuideMainPage;
        this.naverFinancialSummaryPage = NaverFinancialSummaryPage.of(
                naverAnnualFinancialSummaryPage,
                naverQuarterFinancialSummaryPage);
    }
}
