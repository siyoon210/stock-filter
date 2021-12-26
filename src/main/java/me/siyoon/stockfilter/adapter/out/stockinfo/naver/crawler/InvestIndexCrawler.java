package me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class InvestIndexCrawler {
    // 투자지표 페이지
    private static final String INVEST_INDEX_PAGE_URL = "https://navercomp.wisereport.co.kr/v2/company/c1040001.aspx?cmp_cd=";
    private static final String STABILITY_INDEX_TAB_BTN_ID = "val_tab3";

    // 투자분석 영역
    private static final String INVEST_ANALYSIS_YEARLY_PERIOD_BTN_ID = "frqTyp0";
    private static final String INVEST_ANALYSIS_QUARTER_PERIOD_BTN_ID = "frqTyp1";
    private static final String INVEST_ANALYSIS_SEARCH_BTN_ID = "hfinGubun";

    public Element yearlyStabilityIndex(WebDriver webDriver, String stockCode) {
        return stabilityIndex(webDriver, stockCode, INVEST_ANALYSIS_YEARLY_PERIOD_BTN_ID);
    }

    public Element quarterStabilityIndex(WebDriver webDriver, String stockCode) {
        return stabilityIndex(webDriver, stockCode, INVEST_ANALYSIS_QUARTER_PERIOD_BTN_ID);
    }

    private Element stabilityIndex(WebDriver webDriver, String stockCode,
                                   String periodBtnId) {
        webDriver.get(INVEST_INDEX_PAGE_URL + stockCode);
        webDriver.findElement(By.id(STABILITY_INDEX_TAB_BTN_ID)).click();
        webDriver.findElement(By.id(periodBtnId)).click();
        webDriver.findElement(By.id(INVEST_ANALYSIS_SEARCH_BTN_ID)).click();
        String pageSource = webDriver.getPageSource();

        Document document = Jsoup.parse(pageSource);
        return document.getElementById("wrapper")
                       .getElementsByTag("table")
                       .get(4);
    }
}
