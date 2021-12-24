package me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.Equity;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial.PerformanceElementParserHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class EquityParser {

    private static final String LABEL = "자본총계";
    private static final int ELEMENT_INDEX = 9;

    public static Equity equity(Element performanceTable, Period period) {
        try {
            Element element = element(performanceTable, period, LABEL, ELEMENT_INDEX);
            return new Equity(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("EquityParser. Table: {}, Period: {}",
                     performanceTable.html(), period.description);
            return Equity.UNKNOWN_VALUE;
        }
    }
}
