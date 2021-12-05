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

    private static final String URL = "https://finance.naver.com/item/main.nhn?code=";

    private final StockCodeReader stockCodeReader;
    private final NaverStockInfoParser naverStockInfoParser;
    private final NaverTradingInfoParser naverTradingInfoParser;

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
            Document document = document(stockCode);

            return StockInfo.builder()
                            .name(naverStockInfoParser.companyName(document))
                            .code(stockCode)
                            .tradingInfo(naverTradingInfoParser.tradingInfo(document))
                            .performances(null)
                            .build();
        } catch (StockInfoParseException e) {
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
