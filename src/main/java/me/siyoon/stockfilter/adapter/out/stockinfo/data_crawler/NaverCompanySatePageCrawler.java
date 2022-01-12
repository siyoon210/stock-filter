package me.siyoon.stockfilter.adapter.out.stockinfo.data_crawler;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverCompanySatePageCrawler {

    private static final String PAGE_URL = "https://navercomp.wisereport.co.kr/v2/company/c1010001.aspx?cmp_cd=";
    private static final String ANNUAL_FIN_SUMMARY_COOKIE = "[{\"cTB00\":\"cns_td21\"}]";
    private static final String QUARTER_FIN_SUMMARY_COOKIE = "[{\"cTB00\":\"cns_td22\"}]";

    public Document annualFinSummaryPage(String stockCode) {
        return companyStatePage(stockCode, cookieSetting(ANNUAL_FIN_SUMMARY_COOKIE));
    }

    public Document quarterFinSummaryPage(String stockCode) {
        return companyStatePage(stockCode, cookieSetting(QUARTER_FIN_SUMMARY_COOKIE));
    }

    private Consumer<WebClient> cookieSetting(String cookieValue) {
        return (WebClient webClient) -> {
            CookieManager cookieManager = webClient.getCookieManager();
            cookieManager.addCookie(new Cookie("navercomp.wisereport.co.kr", "setC1010001",
                                               cookieValue));
        };
    }

    private Document companyStatePage(String stockCode, Consumer<WebClient> setCookie) {
        try (WebClient webClient = new WebClient()) {
            setCookie.accept(webClient);
            HtmlPage page = webClient.getPage(PAGE_URL + stockCode);
            return Jsoup.parse(page.asXml());
        } catch (Exception e) {
            log.warn("Connect 실패 : " + stockCode);
            return companyStatePage(stockCode, setCookie);
        }
    }
}
