package me.siyoon.stockfilter.adapter.out.stockinfo.data_crawler;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockInfoDataCrawler {

    private final NaverMainPageCrawler naverMainPageCrawler;
    private final NaverCompanySatePageCrawler naverCompanySatePageCrawler;

    public List<CrawledData> crawledDatas(List<String> stockCodes) {
        return stockCodes.parallelStream()
                         .map(this::crawledData)
                         .collect(Collectors.toList());
    }

    private CrawledData crawledData(String stockCode) {
        log.info("크롤링 시작 : {}", stockCode);
        return CrawledData.builder()
                          .stockCode(stockCode)
                          .naverMainPage(naverMainPage(stockCode))
                          .naverAnnualFinancialSummaryPage(
                                  naverAnnualFinancialSummaryPage(stockCode))
                          .naverQuarterFinancialSummaryPage(
                                  naverQuarterFinancialSummaryPage(stockCode))
                          .build();
    }

    private Document naverMainPage(String stockCode) {
        return naverMainPageCrawler.mainPage(stockCode);
    }

    private Document naverAnnualFinancialSummaryPage(String stockCode) {
        return naverCompanySatePageCrawler.annualFinancialSummaryPage(stockCode);
    }

    private Document naverQuarterFinancialSummaryPage(String stockCode) {
        return naverCompanySatePageCrawler.quarterFinancialSummaryPage(stockCode);
    }
}
