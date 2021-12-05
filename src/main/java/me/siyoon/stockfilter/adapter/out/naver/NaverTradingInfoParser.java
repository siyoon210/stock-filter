package me.siyoon.stockfilter.adapter.out.naver;

import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.TradingInfo;
import me.siyoon.stockfilter.exception.StockInfoParseException;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class NaverTradingInfoParser {

    public TradingInfo tradingInfo(Document document) {
        Double price = price(document);
        Long tradingVolume = tradingVolume(document);
        return TradingInfo.builder()
                          .price(price)
                          .tradingVolume(tradingVolume)
                          .transactionAmount(price * tradingVolume)
                          .build();
    }

    private Double price(Document document) { //주가
        try {
            String text = document.getElementById("chart_area")
                                  .getElementsByClass("rate_info").get(0)
                                  .getElementsByClass("today").get(0)
                                  .getElementsByClass("no_today").get(0)
                                  .getElementsByTag("span").get(0).text();
            return Double.valueOf(text.replace(",", ""));
        } catch (NumberFormatException e) {
            throw new StockInfoParseException("주가 파싱 실패" + e.getMessage());
        }
    }

    private Long tradingVolume(Document document) { //거래량
        try {
            String text = document.getElementById("chart_area")
                                  .getElementsByClass("rate_info").get(0)
                                  .getElementsByTag("table").get(0)
                                  .getElementsByTag("tbody").get(0)
                                  .getElementsByTag("tr").get(0)
                                  .getElementsByTag("td").get(2)
                                  .getElementsByTag("em").get(0)
                                  .getElementsByClass("blind").get(0)
                                  .text();
            return Long.parseLong(text.replace(",", ""));
        } catch (Exception e) {
            throw new StockInfoParseException("거래량 파싱 실패 " + e.getMessage());
        }
    }
}
