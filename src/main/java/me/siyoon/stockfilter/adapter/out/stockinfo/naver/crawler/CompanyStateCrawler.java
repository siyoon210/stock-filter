package me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class CompanyStateCrawler {
    // 기업현황 페이지
    private static final String COMPANY_STATE_PAGE_URL = "https://navercomp.wisereport.co.kr/v2/company/c1010001.aspx?cmp_cd=";

    // Financial Summary 테이블
    private static final String YEARLY_FINANCIAL_SUMMARY_SELECTION_BTN_ID = "cns_td21";
    private static final String QUARTER_FINANCIAL_SUMMARY_SELECTION_BTN_ID = "cns_Tab22";
    private static final int FINANCIAL_SUMMARY_TABLE_INDEX = 12;

    Element yearlyFinancialSummary(WebDriver webDriver, String stockCode) {
        return financialSummaryTable(webDriver, stockCode,
                                     YEARLY_FINANCIAL_SUMMARY_SELECTION_BTN_ID);
    }

    Element quarterFinancialSummary(WebDriver webDriver, String stockCode) {
        return financialSummaryTable(webDriver, stockCode,
                                     QUARTER_FINANCIAL_SUMMARY_SELECTION_BTN_ID);
    }

    private Element financialSummaryTable(WebDriver webDriver, String stockCode,
                                          String periodSelectionBtnID) {
        webDriver.get(COMPANY_STATE_PAGE_URL + stockCode);
        webDriver.findElement(By.id(periodSelectionBtnID)).click();
        String pageSource = webDriver.getPageSource();

        Document document = Jsoup.parse(pageSource);
        return document.getElementsByTag("table")
                       .get(FINANCIAL_SUMMARY_TABLE_INDEX);
    }
}
