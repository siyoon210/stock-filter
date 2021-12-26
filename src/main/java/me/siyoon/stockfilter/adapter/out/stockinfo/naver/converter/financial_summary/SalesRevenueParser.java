package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.SalesRevenue;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary.FinancialSummaryParseHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SalesRevenueParser {

    private static final String LABEL = "매출액";
    private static final int ELEMENT_INDEX = 0;

    public static SalesRevenue salesRevenue(CrawledData crawledData, Period period) {
        Element financialSummary = crawledData.financialSummary(period);
        try {
            Element element = element(financialSummary, period, LABEL, ELEMENT_INDEX);
            return SalesRevenue.from(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("SalesRevenueParser. financialSummary: {}, period: {}",
                     financialSummary, period.description);
            return SalesRevenue.UNKNOWN_VALUE;
        }
    }
}
