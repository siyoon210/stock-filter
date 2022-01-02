package me.siyoon.stockfilter.adapter.out.stockinfo.data_crawler;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
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
public class NaverCompanySatePageCrawler {

    public static final String PAGE_URL = "https://navercomp.wisereport.co.kr/v2/company/c1010001.aspx?cmp_cd=";

    public Document page(String stockCode) {
        try (WebClient webClient = new WebClient()){
            setCookie(webClient);
            HtmlPage page = webClient.getPage(PAGE_URL + stockCode);
            return Jsoup.parse(page.asXml());
        } catch (IOException e) {
            throw new StockInfoConnectException("Connect 실패 : " + stockCode);
        }
    }

    private void setCookie(WebClient webClient) {
        CookieManager cookieManager = webClient.getCookieManager();
        cookieManager.addCookie(new Cookie("navercomp.wisereport.co.kr", "setC1010001",
                                           "[{\"cTB00\":\"cns_td21\"}]"));
    }
}
