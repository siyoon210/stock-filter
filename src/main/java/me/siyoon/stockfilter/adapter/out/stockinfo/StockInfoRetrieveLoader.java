package me.siyoon.stockfilter.adapter.out.stockinfo;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockcode.StockCodeReader;
import me.siyoon.stockfilter.adapter.out.stockinfo.data_crawler.StockInfoDataCrawler;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.StockInfoExtractor;
import me.siyoon.stockfilter.application.port.out.StockInfoRetrievePort;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockInfoRetrieveLoader implements StockInfoRetrievePort {

    private final StockCodeReader stockCodeReader;
    private final StockInfoDataCrawler stockInfoDataCrawler;
    private final StockInfoExtractor stockInfoExtractor;

    @Override
    public List<StockInfo> loadedStockInfos() {
        List<String> stockCodes = stockCodeReader.stockCodes();
        List<CrawledData> crawledDatas = stockInfoDataCrawler.crawledDatas(stockCodes);
        return stockInfoExtractor.stockInfos(crawledDatas);
    }
}
