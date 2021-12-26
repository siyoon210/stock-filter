package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.PER;
import me.siyoon.stockfilter.domain.Period;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance.PerformanceElementParserHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class PerParser {

    private static final String LABEL = "PER(배)";
    private static final int ELEMENT_INDEX = 26;

    public static PER per(Element performanceTable, Period period) {
        try {
            Element element = element(performanceTable, period, LABEL, ELEMENT_INDEX);
            return new PER(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("PerParser. Table: {}, Period: {}",
                     performanceTable.html(), period.description);
            return PER.UNKNOWN_VALUE;
        }
    }
}
