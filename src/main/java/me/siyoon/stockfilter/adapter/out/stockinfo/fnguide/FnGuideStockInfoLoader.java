package me.siyoon.stockfilter.adapter.out.stockinfo.fnguide;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockcode.StockCodeReader;
import me.siyoon.stockfilter.application.port.out.LoadStockInfoPort;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FnGuideStockInfoLoader implements LoadStockInfoPort {

    private final StockCodeReader stockCodeReader;
//    private final StockInfoCrawler stockInfoCrawler;
//    private final CrawledDataConverter crawledDataConverter;

    @Override
    public List<StockInfo> loadedStockInfos() {
        List<String> stockCodes = stockCodeReader.stockCodes();
//        List<CrawledData> crawledDatas = stockInfoCrawler.crawledDatas(stockCodes);
//        return crawledDataConverter.stockInfos(crawledDatas);
        return null;
    }
}
