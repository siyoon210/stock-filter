package me.siyoon.stockfilter.adapter.out.stockinfo.fnguide.converter.performance;

import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.OperatingIncome;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import static me.siyoon.stockfilter.adapter.out.stockinfo.fnguide.converter.performance.PerformanceParseHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@Component
public class OperatingIncomeParser {

    private static final String LABEL = "영업이익";
    private static final int ELEMENT_INDEX = 1;

    public OperatingIncome operatingIncome(CrawledData crawledData, Period period) {
        try {
            Element element = element(crawledData, period, LABEL, ELEMENT_INDEX);
            return OperatingIncome.from(doubleValue(element.text()));
        } catch (NullPointerException ignored) {
            return OperatingIncome.UNKNOWN_VALUE;
        } catch (Exception e) {
            log.warn("OperatingIncomeParser. data: {}, period: {}", crawledData, period, e);
            return OperatingIncome.UNKNOWN_VALUE;
        }
    }
}
