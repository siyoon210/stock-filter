package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.domain.TradingInfo;
import me.siyoon.stockfilter.exception.StockInfoParseException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.longValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TradingInfoExtractor {

    public static TradingInfo tradingInfo(CrawledData crawledData) {
        try {
            Double price = price(crawledData.naverMainPage);
            Long tradingVolume = tradingVolume(crawledData.naverMainPage);
            Long numberOfShares = numberOfShares(crawledData.naverMainPage);
            return TradingInfo.builder()
                              .price(price)
                              .numberOfShare(numberOfShares)
                              .tradingVolume(tradingVolume)
                              .transactionAmount(price * tradingVolume)
                              .annualHigh(annualHigh(crawledData.naverMainPage))
                              .annualLow(annualLow(crawledData.naverMainPage))
                              .build();
        } catch (StockInfoParseException e) {
            log.info("TradingInfo 파싱실패. StockCode = {}", crawledData.stockCode);
            return TradingInfo.EMPTY;
        }
    }

    private static Double price(Document naverMainPage) { // 주가
        try {
            String text = naverMainPage.getElementById("chart_area")
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

    private static Long tradingVolume(Document naverMainPage) { // 거래량
        try {
            String text = naverMainPage.getElementById("chart_area")
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

    private static Double annualHigh(Document naverMainPage) { // 52주 최고가
        try {
            String text = naverMainPage.getElementById("tab_con1")
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

    private static Double annualLow(Document naverMainPage) { // 52주 최저가
        try {
            String text = naverMainPage.getElementById("tab_con1")
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

    private static Long numberOfShares(Document naverMainPage) { // 주식수
        try {
            Element numberOfShares = naverMainPage.getElementById("tab_con1")
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
