package me.siyoon.stockfilter.adapter.out.naver;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockcode.StockCodeReader;
import me.siyoon.stockfilter.application.port.out.LoadStockInfoPort;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.exception.StockInfoConnectException;
import me.siyoon.stockfilter.exception.StockInfoParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverStockInfoCrawler implements LoadStockInfoPort {

    public static final String URL = "https://finance.naver.com/item/main.nhn?code=";

    private final StockCodeReader stockCodeReader;
    private final NaverStockInfoParser stockInfoParser;
    private final NaverTradingInfoParser tradingInfoParser;
    private final NaverPerformanceParser performanceParser;

    @Override
    public List<StockInfo> stockInfos() {
        List<String> stockCodes = stockCodeReader.stockCodes();
        return stockCodes.parallelStream()
                         .map(this::stockInfo)
                         .filter(Objects::nonNull)
                         .collect(Collectors.toList());
    }

    private StockInfo stockInfo(String stockCode) {
        try {
            log.info("크롤링 시작. URL = {}", NaverStockInfoCrawler.URL + stockCode);
            Document document = document(stockCode);

            return StockInfo.builder()
                            .name(stockInfoParser.companyName(document))
                            .code(stockCode)
                            .tradingInfo(tradingInfoParser.tradingInfo(document))
                            .performances(performanceParser.performances(document))
                            .build();
        } catch (StockInfoParseException e) {
            log.warn("StockInfoParseException. {}", e.getMessage());
            return null;
        }
    }

    private static Document document(String stockCode) {
        String stockInfoUrl = URL + stockCode;
        try {
            return Jsoup.connect(stockInfoUrl).get();
        } catch (IOException e) {
            throw new StockInfoConnectException("Connect 실패 : " + stockInfoUrl);
        }
    }
}
