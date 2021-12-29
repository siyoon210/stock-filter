package me.siyoon.stockfilter.adapter.out.stockinfo.fnguide.converter.performance;

import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.SalesRevenue;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import static me.siyoon.stockfilter.adapter.out.stockinfo.fnguide.converter.performance.PerformanceParseHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@Component
public class SalesRevenueParser {

    private static final String LABEL = "매출액";
    private static final int ELEMENT_INDEX = 0;

    public SalesRevenue salesRevenue(CrawledData crawledData, Period period) {
        try {
            Element element = element(crawledData, period, LABEL, ELEMENT_INDEX);
            return SalesRevenue.from(doubleValue(element.text()));
        } catch (NullPointerException ignored) {
            return SalesRevenue.UNKNOWN_VALUE;
        } catch (Exception e) {
            log.warn("SalesRevenueParser. data: {}, period: {}", crawledData, period, e);
            return SalesRevenue.UNKNOWN_VALUE;
        }
    }
}
