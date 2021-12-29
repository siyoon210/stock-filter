package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.main_performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.ReserveRatio;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.main_performance.MainPerformanceParseHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReserveRatioParser {

    private static final String LABEL = "유보율";
    private static final int ELEMENT_INDEX = 8;

    public static ReserveRatio reserveRatio(CrawledData crawledData, Period period) {
        Element performanceTable = crawledData.performanceTable();
        try {
            Element element = element(performanceTable, period, LABEL, ELEMENT_INDEX);
            return ReserveRatio.from(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("ReserveRatioParser. performanceTable: {}, period: {}",
                     performanceTable, period.description);
            return ReserveRatio.UNKNOWN_VALUE;
        }
    }
}
