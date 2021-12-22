package me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.NetIncome;
import me.siyoon.stockfilter.domain.Period;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial.PerformanceElementParserHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class NetIncomeParser {

    private static final String LABEL = "당기순이익";
    private static final int ELEMENT_INDEX = 4;

    public static NetIncome netIncome(Element performanceTable, Period period) {
        try {
            Element element = element(performanceTable, period, LABEL, ELEMENT_INDEX);
            return new NetIncome(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("NetIncomeParser. Table: {}, Period: {}",
                     performanceTable.html(), period.description);
            return NetIncome.UNKNOWN_VALUE;
        }
    }
}
