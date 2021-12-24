package me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.DPS;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial.PerformanceElementParserHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class DpsParser {

    private static final String LABEL = "현금DPS(원)";
    private static final int ELEMENT_INDEX = 29;

    public static DPS dps(Element performanceTable, Period period) {
        try {
            Element element = element(performanceTable, period, LABEL, ELEMENT_INDEX);
            return new DPS(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("DpsParser. Table: {}, Period: {}",
                     performanceTable.html(), period.description);
            return DPS.UNKNOWN_VALUE;
        }
    }
}
