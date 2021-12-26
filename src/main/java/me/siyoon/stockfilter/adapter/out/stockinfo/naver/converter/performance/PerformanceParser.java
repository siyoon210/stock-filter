package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.Performance;
import me.siyoon.stockfilter.domain.performance.Performances;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toMap;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance.CfoParser.cfo;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance.DividendYieldParser.dividendYield;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance.DpsParser.dps;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance.EquityParser.equity;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance.NetIncomeParser.netIncome;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance.OperatingIncomeParser.operatingIncome;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance.PerParser.per;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance.SalesRevenueParser.salesRevenue;

@Slf4j
@Component
public class PerformanceParser {

    private static final String URL_FORMAT = "https://navercomp.wisereport.co.kr/v2/company/c1010001.aspx?cmp_cd=%s";

    private static final String YEARLY_TABLE_BTN_ID = "cns_td21";
    private static final String QUARTER_TABLE_BTN_ID = "cns_Tab22";
    private static final int PERFORMANCE_TABLE_INDEX = 12;

    public Performances performances(WebDriver driver, String code) {
        return new Performances(performanceByPeriod(driver, code));
    }

    private Map<Period, Performance> performanceByPeriod(WebDriver driver, String code) {
        Map<Period, Performance> performanceByYears
                = performancesMap(yearlyPerformanceTable(code, driver), Period.YEARS);
        Map<Period, Performance> performanceByQuarters
                = performancesMap(quartersPerformanceTable(code, driver), Period.QUARTERS);

        performanceByYears.putAll(performanceByQuarters);
        return performanceByYears;
    }

    private Element yearlyPerformanceTable(String code, WebDriver driver) {
        return performanceTable(code, driver, YEARLY_TABLE_BTN_ID);
    }

    private Element quartersPerformanceTable(String code, WebDriver driver) {
        return performanceTable(code, driver, QUARTER_TABLE_BTN_ID);
    }

    private Element performanceTable(String code, WebDriver driver, String btnId) {
        driver.get(String.format(URL_FORMAT, code));
        driver.findElement(By.id(btnId)).click();
        String pageSource = driver.getPageSource();

        Document document = Jsoup.parse(pageSource);
        return document.getElementsByTag("table")
                       .get(PERFORMANCE_TABLE_INDEX);
    }

    private Map<Period, Performance> performancesMap(Element performanceTable,
                                                     List<Period> periods) {
        return periods.stream()
                      .collect(toMap(period -> period,
                                     period -> performance(performanceTable, period),
                                     (x, y) -> y,
                                     LinkedHashMap::new));
    }

    private Performance performance(Element performanceTable, Period period) {
        return Performance.builder()
                          .salesRevenue(salesRevenue(performanceTable, period))
                          .operatingIncome(operatingIncome(performanceTable, period))
                          .netIncome(netIncome(performanceTable, period))
                          .debtRatio(null)
                          .quickRatio(null)
                          .reserveRatio(null)
                          .equity(equity(performanceTable, period))
                          .cfo(cfo(performanceTable, period))
                          .per(per(performanceTable, period))
                          .dps(dps(performanceTable, period))
                          .dividendYield(dividendYield(performanceTable, period))
                          .build();
    }
}
