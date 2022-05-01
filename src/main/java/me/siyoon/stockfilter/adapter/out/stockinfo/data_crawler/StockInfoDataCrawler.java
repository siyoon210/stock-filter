package me.siyoon.stockfilter.adapter.out.stockinfo.data_crawler;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

    @SneakyThrows
    public List<CrawledData> crawledDatas(List<String> stockCodes) {
        AtomicInteger progress = new AtomicInteger();
        return stockCodes.parallelStream()
                         .map(stockCode -> {
                             CrawledData crawledData = crawledData(stockCode);
                             logProgress(stockCodes.size(), progress, stockCode);
                             return crawledData;
                         })
                         .collect(Collectors.toList());
    }

    private void logProgress(int size, AtomicInteger progress, String stockCode) {
        log.info("크롤링 완료. stockCode= {}  ({} / {})",
                 stockCode, progress.incrementAndGet(), size);
    }

    private CrawledData crawledData(String stockCode) {
        return CrawledData.builder()
                          .stockCode(stockCode)
                          .naverMainPage(naverMainPage(stockCode))
                          .naverAnnualFinSummaryPage(naverAnnualFinSummaryPage(stockCode))
//                          .naverQuarterFinSummaryPage(naverQuarterFinSummaryPage(stockCode))
                          .build();
    }

    private Document naverMainPage(String stockCode) {
        return naverMainPageCrawler.mainPage(stockCode);
    }

    private Document naverAnnualFinSummaryPage(String stockCode) {
        return naverCompanySatePageCrawler.annualFinSummaryPage(stockCode);
    }

    private Document naverQuarterFinSummaryPage(String stockCode) {
        return naverCompanySatePageCrawler.quarterFinSummaryPage(stockCode);
    }
}
