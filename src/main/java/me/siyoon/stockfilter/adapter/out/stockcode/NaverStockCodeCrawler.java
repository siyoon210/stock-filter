package me.siyoon.stockfilter.adapter.out.stockcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.exception.StockInfoConnectException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@Slf4j
public class NaverStockCodeCrawler implements StockCodeReader {

    private static final String STOCK_LIST_URL_FORMAT = "https://finance.naver.com/sise/sise_market_sum.naver?sosok=%s&page=%d";
    private static final String KOSPI_CODE = "0";
    private static final String KOSDAQ_CODE = "1";
    private static final String ITEM_PATH = "/item/main.naver?code=";

    @Override
    public List<String> stockCodes() {
        List<String> stockCodes = new ArrayList<>();
        stockCodes.addAll(kospiStockCodes());
        stockCodes.addAll(kosdaqStockCodes());
        return stockCodes;
    }

    private List<String> kospiStockCodes() {
        List<String> kospiStockCodeList = new ArrayList<>();

        int lastKospiPage = 35;
        addStockCodes(kospiStockCodeList, lastKospiPage, KOSPI_CODE);

        return kospiStockCodeList;
    }

    private List<String> kosdaqStockCodes() {
        List<String> kosdaqStockCodes = new ArrayList<>();

        int lastKosdaqPage = 31;
        addStockCodes(kosdaqStockCodes, lastKosdaqPage, KOSDAQ_CODE);

        return kosdaqStockCodes;
    }

    private void addStockCodes(List<String> stockCodeList, int lastKospiPage, String kospiCode) {
        for (int page = 1; page <= lastKospiPage; page++) {
            Document document = document(kospiCode, page);
            Elements elementsByTag = document.getElementsByTag("table").get(1)
                                             .getElementsByTag("tbody").get(0)
                                             .getElementsByTag("tr");
            for (Element element : elementsByTag) {
                try {
                    String link = element.getElementsByTag("td").get(1)
                                         .getElementsByTag("a").get(0).attr("href");

                    String code = link.substring(ITEM_PATH.length());
                    stockCodeList.add(code);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private static Document document(String marketCode, int page) {
        String stockListUrl = String.format(STOCK_LIST_URL_FORMAT, marketCode, page);
        try {
            return Jsoup.connect(stockListUrl).get();
        } catch (IOException e) {
            throw new StockInfoConnectException("Connect 실패 : " + stockListUrl);
        }
    }
}
