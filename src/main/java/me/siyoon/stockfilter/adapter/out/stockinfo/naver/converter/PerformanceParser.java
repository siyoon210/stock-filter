package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.Performance;
import me.siyoon.stockfilter.domain.performance.Performances;

import static java.util.stream.Collectors.toMap;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary.CfoParser.cfo;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary.DividendYieldParser.dividendYield;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary.DpsParser.dps;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary.EquityParser.equity;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary.NetIncomeParser.netIncome;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary.OperatingIncomeParser.operatingIncome;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary.PerParser.per;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary.SalesRevenueParser.salesRevenue;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.stability_index.DebtRatioParser.debtRatio;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.stability_index.QuickRatioParser.quickRatio;
import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.stability_index.ReserveRatioParser.reserveRatio;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerformanceParser {

    public static Performances performances(CrawledData crawledData) {
        Map<Period, Performance> periodPerformanceMap = performancesMap(crawledData);
        return new Performances(periodPerformanceMap);
    }

    private static Map<Period, Performance> performancesMap(CrawledData crawledData) {
        return Arrays.stream(Period.values())
                     .collect(toMap(period -> period,
                                    period -> performance(crawledData, period),
                                    (x, y) -> y,
                                    LinkedHashMap::new));
    }

    private static Performance performance(CrawledData crawledData, Period period) {
        return Performance.builder()
                          .salesRevenue(salesRevenue(crawledData, period))
                          .operatingIncome(operatingIncome(crawledData, period))
                          .netIncome(netIncome(crawledData, period))
                          .debtRatio(debtRatio(crawledData, period))
                          .quickRatio(quickRatio(crawledData, period))
                          .reserveRatio(reserveRatio(crawledData, period))
                          .equity(equity(crawledData, period))
                          .cfo(cfo(crawledData, period))
                          .per(per(crawledData, period))
                          .dps(dps(crawledData, period))
                          .dividendYield(dividendYield(crawledData, period))
                          .build();
    }
}
