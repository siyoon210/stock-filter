package me.siyoon.stockfilter.adapter.out.stockinfo.data_crawler;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.exception.StockInfoConnectException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverMainPageCrawler {
    public static final String MAIN_PAGE_URL = "https://finance.naver.com/item/main.nhn?code=";

    public Document mainPage(String stockCode) {
        String stockInfoUrl = MAIN_PAGE_URL + stockCode;
        try {
            return Jsoup.connect(stockInfoUrl).get();
        } catch (IOException e) {
            throw new StockInfoConnectException("Connect 실패 : " + stockInfoUrl);
        }
    }
}
