package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.PerformanceExtractor;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockInfoExtractor {

    public List<StockInfo> stockInfosFrom(List<CrawledData> crawledDatas) {
        return crawledDatas.parallelStream()
                           .map(this::stockInfo)
                           .collect(Collectors.toList());
    }

    private StockInfo stockInfo(CrawledData crawledData) {
        return StockInfo.builder()
                        .name(BasicInfoExtractor.companyName(crawledData))
                        .code(crawledData.stockCode)
                        .tradingInfo(TradingInfoExtractor.tradingInfo(crawledData))
                        .performances(PerformanceExtractor.performances(crawledData))
                        .build();
    }
}
