package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.Performance;
import me.siyoon.stockfilter.domain.performance.Performances;

import static java.util.stream.Collectors.toMap;
import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.DebtRatioExtractor.debtRatio;
import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.EpsExtractor.eps;
import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.NetIncomeExtractor.netIncome;
import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.OperatingIncomeExtractor.operatingIncome;
import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.QuickRatioExtractor.quickRatio;
import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.ReserveRatioExtractor.reserveRatio;
import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.SalesRevenueExtractor.salesRevenue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerformanceExtractor {

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
        try {
            return Performance.builder()
                              .salesRevenue(salesRevenue(crawledData, period))
                              .operatingIncome(operatingIncome(crawledData, period))
                              .netIncome(netIncome(crawledData, period))
                              .debtRatio(debtRatio(crawledData, period))
                              .quickRatio(quickRatio(crawledData, period))
                              .reserveRatio(reserveRatio(crawledData, period))
                              .eps(eps(crawledData, period))
                              .build();
        } catch (Exception e) {
            log.warn("Performance 파싱 실패. stockCode = {}, period = {}",
                     crawledData.stockCode, period);
            return Performance.UNKNOWN_VALUE;
        }
    }
}
