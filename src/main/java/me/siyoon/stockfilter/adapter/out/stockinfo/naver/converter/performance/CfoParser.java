package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.CFO;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance.PerformanceElementParserHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CfoParser {

    private static final String LABEL = "영업활동현금흐름";
    private static final int ELEMENT_INDEX = 13;

    public static CFO cfo(Element performanceTable, Period period) {
        try {
            Element element = element(performanceTable, period, LABEL, ELEMENT_INDEX);
            return new CFO(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("CfoParser. Table: {}, Period: {}",
                     performanceTable.html(), period.description);
            return CFO.UNKNOWN_VALUE;
        }
    }
}
