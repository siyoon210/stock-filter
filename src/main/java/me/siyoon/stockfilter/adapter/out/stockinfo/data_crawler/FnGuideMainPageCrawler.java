package me.siyoon.stockfilter.adapter.out.stockinfo.data_crawler;

import java.io.IOException;
import me.siyoon.stockfilter.exception.StockInfoConnectException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class FnGuideMainPageCrawler {

    private static final String URL_FORMAT = "https://comp.fnguide.com/SVO2/asp/SVD_Main.asp?pGB=1&gicode=A%s&cID=&MenuYn=Y&ReportGB=&NewMenuID=101&stkGb=701";

    public Document mainPage(String stockCode) {
        String targetUrl = String.format(URL_FORMAT, stockCode);
        try {
            return Jsoup.connect(targetUrl).get();
        } catch (IOException e) {
            throw new StockInfoConnectException("Connect 실패 : " + targetUrl);
        }
    }
}
