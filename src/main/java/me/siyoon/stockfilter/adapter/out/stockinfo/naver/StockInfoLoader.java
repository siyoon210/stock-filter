package me.siyoon.stockfilter.adapter.out.stockinfo.naver;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockcode.StockCodeReader;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.CrawledDataConverter;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.StockInfoCrawler;
import me.siyoon.stockfilter.application.port.out.LoadStockInfoPort;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockInfoLoader implements LoadStockInfoPort {

    private final StockCodeReader stockCodeReader;
    private final StockInfoCrawler stockInfoCrawler;
    private final CrawledDataConverter crawledDataConverter;

    @Override
    public List<StockInfo> loadedStockInfos() {
        List<String> stockCodes = stockCodeReader.stockCodes();
        List<CrawledData> crawledDatas = stockInfoCrawler.crawledDatas(stockCodes);
        return crawledDataConverter.stockInfos(crawledDatas);
    }
}
