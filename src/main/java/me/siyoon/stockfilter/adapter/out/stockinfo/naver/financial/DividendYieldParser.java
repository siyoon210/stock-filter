package me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.DividendYield;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial.PerformanceElementParserHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class DividendYieldParser {

    private static final String LABEL = "현금배당수익률";
    private static final int ELEMENT_INDEX = 30;

    public static DividendYield dividendYield(Element performanceTable, Period period) {
        try {
            Element element = element(performanceTable, period, LABEL, ELEMENT_INDEX);
            return new DividendYield(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("DividendYieldParser. Table: {}, Period: {}",
                     performanceTable.html(), period.description);
            return DividendYield.UNKNOWN_VALUE;
        }
    }
}
