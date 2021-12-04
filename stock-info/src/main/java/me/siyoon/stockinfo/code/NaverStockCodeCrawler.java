package me.siyoon.stockinfo.code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static me.siyoon.stockinfo.Main.LOGGER;

public class NaverStockCodeCrawler {

    private static final String STOCK_ITME_LIST_URL_FORMAT = "https://finance.naver.com/sise/sise_market_sum.naver?sosok=%s&page=%d";
    private static final String KOSPI_SOSOK_CODE = "0";
    private static final String KOSDAQ_SOSOK_CODE = "1";
    private static final String ITEM_PATH = "/item/main.naver?code=";

    public List<String> stockCodeList() {
        List<String> stockCodeList = new ArrayList<>();
        stockCodeList.addAll(kospiStockCodeList());
        stockCodeList.addAll(kosdaqStockCodeList());
        return stockCodeList;
    }

    private List<String> kospiStockCodeList() {
        //맨뒤 페이지 추출
        List<String> stockCodeList = new ArrayList<>();

        int lastKospiPage = lastKospiPage();
        for (int i = 1; i <= lastKospiPage; i++) {
            Document document = getDocument(KOSPI_SOSOK_CODE, i);
            Elements elementsByTag = document.getElementsByTag("table").get(1)
                                             .getElementsByTag("tbody").get(0)
                                             .getElementsByTag("tr");
            for (Element element : elementsByTag) {
                try {
                    String link = element.getElementsByTag("td").get(1)
                                         .getElementsByTag("a").get(0).attr("href");

                    String code = link.substring(ITEM_PATH.length());
                    stockCodeList.add(code);
                } catch (Exception e) {
                    continue;
                }
            }
        }

        return stockCodeList;
    }

    private List<String> kosdaqStockCodeList() {
        //맨뒤 페이지 추출
        List<String> stockCodeList = new ArrayList<>();

        int lastKosdaqPage = lastKosdaqPage();
        for (int i = 1; i <= lastKosdaqPage; i++) {
            Document document = getDocument(KOSDAQ_SOSOK_CODE, i);
            Elements elementsByTag = document.getElementsByTag("table").get(1)
                                             .getElementsByTag("tbody").get(0)
                                             .getElementsByTag("tr");
            for (Element element : elementsByTag) {
                try {
                    String link = element.getElementsByTag("td").get(1)
                                         .getElementsByTag("a").get(0).attr("href");

                    String code = link.substring(ITEM_PATH.length());
                    stockCodeList.add(code);
                } catch (Exception e) {
                    continue;
                }
            }
        }

        return stockCodeList;
    }

    private int lastKospiPage() {
        return 35;
    }

    private int lastKosdaqPage() {
        return 31;
    }

    private static Document getDocument(String sosokCode, int page) {
        try {
            return Jsoup.connect(String.format(STOCK_ITME_LIST_URL_FORMAT, sosokCode, page)).get();
        } catch (IOException e) {
            LOGGER.warning("URL 파싱 실패");
            return null;
        }
    }
}
