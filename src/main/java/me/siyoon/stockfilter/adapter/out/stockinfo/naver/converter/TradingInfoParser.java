package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.TradingInfo;
import me.siyoon.stockfilter.exception.StockInfoParseException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TradingInfoParser {

    public static TradingInfo tradingInfo(Document document) {
        Double price = price(document);
        Long tradingVolume = tradingVolume(document);
        Long numberOfShares = numberOfShares(document);
        return TradingInfo.builder()
                          .marketCap(price * numberOfShares)
                          .price(price)
                          .numberOfShare(numberOfShares)
                          .tradingVolume(tradingVolume)
                          .transactionAmount(price * tradingVolume)
                          .annualHigh(annualHigh(document))
                          .annualLow(annualLow(document))
                          .build();
    }

    private static Double price(Document document) { // 주가
        try {
            String text = document.getElementById("chart_area")
                                  .getElementsByClass("rate_info").get(0)
                                  .getElementsByClass("today").get(0)
                                  .getElementsByClass("no_today").get(0)
                                  .getElementsByTag("span").get(0)
                                  .text().replace(",", "");
            return Double.valueOf(text);
        } catch (NumberFormatException e) {
            throw new StockInfoParseException("주가 파싱 실패" + e.getMessage());
        }
    }

    private static Long tradingVolume(Document document) { // 거래량
        try {
            String text = document.getElementById("chart_area")
                                  .getElementsByClass("rate_info").get(0)
                                  .getElementsByTag("table").get(0)
                                  .getElementsByTag("tbody").get(0)
                                  .getElementsByTag("tr").get(0)
                                  .getElementsByTag("td").get(2)
                                  .getElementsByTag("em").get(0)
                                  .getElementsByClass("blind").get(0)
                                  .text().replace(",", "");
            return Long.parseLong(text);
        } catch (Exception e) {
            throw new StockInfoParseException("거래량 파싱 실패 " + e.getMessage());
        }
    }

    private Double annualHigh(Document document) { // 52주 최고가
        try {
            String text = document.getElementById("tab_con1")
                                  .getElementsByTag("div").get(5)
                                  .getElementsByTag("table").get(0)
                                  .getElementsByTag("tbody").get(0)
                                  .getElementsByTag("tr").get(1)
                                  .getElementsByTag("td").get(0)
                                  .getElementsByTag("em").get(0)
                                  .text().replace(",", "");
            return Double.parseDouble(text);
        } catch (Exception e) {
            throw new StockInfoParseException("52주 최고가 파싱 실패 " + e.getMessage());
        }
    }

    private Double annualLow(Document document) { // 52주 최저가
        try {
            String text = document.getElementById("tab_con1")
                                  .getElementsByTag("div").get(5)
                                  .getElementsByTag("table").get(0)
                                  .getElementsByTag("tbody").get(0)
                                  .getElementsByTag("tr").get(1)
                                  .getElementsByTag("td").get(0)
                                  .getElementsByTag("em").get(1)
                                  .text().replace(",", "");
            return Double.parseDouble(text);
        } catch (Exception e) {
            throw new StockInfoParseException("52주 최저가 파싱 실패 " + e.getMessage());
        }
    }

    private Long numberOfShares(Document document) { // 주식수
        try {
            Element numberOfShares = document.getElementById("tab_con1")
                                      .getElementsByClass("first").get(0)
                                      .getElementsByTag("table").get(0)
                                      .getElementsByTag("tbody").get(0)
                                      .getElementsByTag("tr").get(2)
                                      .getElementsByTag("td").get(0)
                                      .getElementsByTag("em").get(0);
            return longValue(numberOfShares.text());
        } catch (Exception e) {
            throw new StockInfoParseException("상장주식수 파싱 실패 " + e.getMessage());
        }
    }
}
