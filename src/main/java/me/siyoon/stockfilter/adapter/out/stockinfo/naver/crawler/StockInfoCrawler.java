package me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockInfoCrawler {

    private final MainPageCrawler mainPageCrawler;
    private final CompanyStateCrawler companyStateCrawler;
    private final InvestIndexCrawler investIndexCrawler;

    public List<CrawledData> loadedStockInfos(List<String> stockCodes) {
        WebDriver webDriver = new HtmlUnitDriver(BrowserVersion.CHROME, true);
        try {
            return stockCodes.stream()
                             .map(stockCode -> crawledData(webDriver, stockCode))
                             .collect(Collectors.toList());
        } finally {
            webDriver.quit();
        }
    }

    private CrawledData crawledData(WebDriver webDriver, String stockCode) {
        return CrawledData.builder()
                          .mainPage(mainPageCrawler.mainPage(stockCode))
                          .yearlyFinancialSummary(
                                  companyStateCrawler.yearlyFinancialSummary(webDriver, stockCode))
                          .quarterFinancialSummary(
                                  companyStateCrawler.quarterFinancialSummary(webDriver, stockCode))
                          .yearlyStabilityIndex(
                                  investIndexCrawler.yearlyStabilityIndex(webDriver, stockCode))
                          .quarterStabilityIndex(
                                  investIndexCrawler.quarterStabilityIndex(webDriver, stockCode))
                          .build();
    }
}
