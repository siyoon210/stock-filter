package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.naver.fin_summary.NaverFinSummaryExtractHelper;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.naver.fin_summary.NaverFinSummaryExtractParam;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.SalesRevenue;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.PerformanceExceptionHandler.handle;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SalesRevenueExtractor {

    private static final NaverFinSummaryExtractParam EXTRACT_PARAM;

    static {
        EXTRACT_PARAM = NaverFinSummaryExtractParam.builder()
                                                   .expectedLabel("매출액")
                                                   .elementIndex(0)
                                                   .build();
    }

    public static SalesRevenue salesRevenue(CrawledData crawledData, Period period) {
        try {
            Element element = NaverFinSummaryExtractHelper.element(crawledData, period,
                                                                   EXTRACT_PARAM);
            return SalesRevenue.from(doubleValue(element.text()));
        } catch (RuntimeException e) {
            return (SalesRevenue) handle(e, crawledData.stockCode, period,
                                         SalesRevenue.UNKNOWN_VALUE);
        }
    }
}
