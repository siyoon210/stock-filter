package me.siyoon.stockfilter.adapter.out.stockinfo.naver;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance.NaverDebtRatioParser;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance.NaverDividendYieldParser;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance.NaverDpsParser;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance.NaverNetIncomeParser;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance.NaverOperatingIncomeParser;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance.NaverPerParser;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance.NaverPerformanceParseHelper;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance.NaverQuickRatioParser;
import me.siyoon.stockfilter.domain.Performance;
import me.siyoon.stockfilter.domain.Performances;
import me.siyoon.stockfilter.domain.Period;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NaverPerformanceParser {

    public Performances performances(Document document) {
        Element performanceTable = NaverPerformanceParseHelper.performanceTable(document);

        Map<Period, Performance> value =
                Arrays.stream(Period.values())
                      .collect(Collectors.toMap(period -> period,
                                                period -> performance(period, performanceTable)));
        return new Performances(value);
    }

    private Performance performance(Period period, Element performanceTable) {

        return Performance.builder()
                          .operatingIncome(NaverOperatingIncomeParser.operatingIncome(period, performanceTable))
                          .netIncome(NaverNetIncomeParser.netIncome(period, performanceTable))
                          .debtRatio(NaverDebtRatioParser.debtRatio(period, performanceTable))
                          .quickRatio(NaverQuickRatioParser.quickRatio(period, performanceTable))
                          .per(NaverPerParser.per(period, performanceTable))
                          .dps(NaverDpsParser.dps(period, performanceTable))
                          .dividendYield(NaverDividendYieldParser.dividendYield(period, performanceTable))
                          .build();
    }
}
