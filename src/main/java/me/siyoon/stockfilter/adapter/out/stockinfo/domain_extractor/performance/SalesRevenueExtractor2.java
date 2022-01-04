package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.SalesRevenue;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.PerformanceExceptionHandler.handle;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SalesRevenueExtractor2 {

    public static SalesRevenue salesRevenue(CrawledData crawledData, Period period) {
        try {
            Element element = crawledData.naverFinancialSummaryPage
                    .document(period)
                    .getElementsByTag("table").get(12)
                    .getElementsByTag("tbody").get(0)
                    .getElementsByTag("tr").get(0) //매출액 엘레먼트 인덱스
                    .getElementsByTag("td").get(4); //작년 period 인덱스
            return SalesRevenue.from(doubleValue(element.text()));
        } catch (RuntimeException e) {
            return (SalesRevenue) handle(e, crawledData.stockCode, period,
                                         SalesRevenue.UNKNOWN_VALUE);
        }
    }
}
