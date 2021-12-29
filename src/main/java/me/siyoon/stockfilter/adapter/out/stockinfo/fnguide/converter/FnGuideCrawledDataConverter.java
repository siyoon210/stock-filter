package me.siyoon.stockfilter.adapter.out.stockinfo.fnguide.converter;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.MainInfoParser;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.PerformanceParser;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.TradingInfoParser;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FnGuideCrawledDataConverter {

    public List<StockInfo> stockInfos(List<CrawledData> crawledDatas) {
        return crawledDatas.stream()
                .map(this::stockInfo)
                .collect(Collectors.toList());
    }

    private StockInfo stockInfo(CrawledData crawledData) {
        return StockInfo.builder()
                        .name(MainInfoParser.companyName(crawledData.mainPage))
                        .code(crawledData.stockCode)
                        .tradingInfo(TradingInfoParser.tradingInfo(crawledData.mainPage))
                        .performances(PerformanceParser.performances(crawledData))
                        .build();
    }
}
