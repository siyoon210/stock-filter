package me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.financial.Performance2;
import me.siyoon.stockfilter.domain.financial.Performances2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toMap;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial.CfoParser.cfo;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial.EquityParser.equity;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial.NetIncomeParser.netIncome;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial.SalesRevenueParser.salesRevenue;

@Slf4j
@Component
public class PerformanceParser {

    private static final String URL_FORMAT = "https://navercomp.wisereport.co.kr/v2/company/c1010001.aspx?cmp_cd=%s";

    private static final String QUARTER_TABLE_BTN_ID = "cns_Tab22";
    private static final int PERFORMANCE_TABLE_INDEX = 12;

    public Performances2 performances(WebDriver driver, String code) {
        Element performanceTable = quartersPerformanceTable(code, driver);
        Map<Period, Performance2> value = performancesMap(performanceTable);
        return new Performances2(value);
    }

    private Element quartersPerformanceTable(String code, WebDriver driver) {
        driver.get(String.format(URL_FORMAT, code));
        driver.findElement(By.id(QUARTER_TABLE_BTN_ID)).click();
        String pageSource = driver.getPageSource();

        Document document = Jsoup.parse(pageSource);
        return document.getElementsByTag("table")
                       .get(PERFORMANCE_TABLE_INDEX);
    }

    private Map<Period, Performance2> performancesMap(Element performanceTable) {
        return Period.QUARTERS.stream()
                              .collect(toMap(period -> period,
                                             period -> performance(performanceTable, period),
                                             (x, y) -> y,
                                             LinkedHashMap::new));
    }

    private Performance2 performance(Element performanceTable, Period period) {
        return Performance2.builder()
                           .salesRevenue(salesRevenue(performanceTable, period))
                           .netIncome(netIncome(performanceTable, period))
                           .equity(equity(performanceTable, period))
                           .cfo(cfo(performanceTable, period))
                           .build();
    }
}
