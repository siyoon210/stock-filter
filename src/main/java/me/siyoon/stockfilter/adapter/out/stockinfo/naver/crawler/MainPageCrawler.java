package me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler;

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
class MainPageCrawler {
    public static final String MAIN_PAGE_URL = "https://finance.naver.com/item/main.nhn?code=";

    Document mainPage(String stockCode) {
        String stockInfoUrl = MAIN_PAGE_URL + stockCode;
        try {
            return Jsoup.connect(stockInfoUrl).get();
        } catch (IOException e) {
            throw new StockInfoConnectException("Connect 실패 : " + stockInfoUrl);
        }
    }
}