package me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.financial.SalesRevenue;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial.PerformanceElementParserHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class SalesRevenueParser {

    private static final String LABEL = "매출액";
    private static final int ELEMENT_INDEX = 0;

    public static SalesRevenue salesRevenue(Element performanceTable, Period period) {
        try {
            Element element = element(performanceTable, period, LABEL, ELEMENT_INDEX);
            return new SalesRevenue(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("SalesRevenueParser. Table: {}, Period: {}",
                     performanceTable.html(), period.description);
            return SalesRevenue.UNKNOWN_VALUE;
        }
    }
}
