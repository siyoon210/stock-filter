package me.siyoon.stockfilter.adapter.out.stockinfo.data_crawler;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockInfoDataCrawler {

    private final NaverMainPageCrawler naverMainPageCrawler;
    private final FnGuideMainPageCrawler fnGuideMainPageCrawler;

    public List<CrawledData> crawledDatas(List<String> stockCodes) {
        return stockCodes.parallelStream()
                         .map(this::crawledData)
                         .collect(Collectors.toList());
    }

    private CrawledData crawledData(String stockCode) {
        log.info("크롤링 시작 : {}", stockCode);
        return CrawledData.builder()
                          .stockCode(stockCode)
                          .naverMainPage(naverMainPageCrawler.mainPage(stockCode))
                          .fnGuideMainPage(fnGuideMainPageCrawler.mainPage(stockCode))
                          .build();
    }
}
