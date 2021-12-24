package me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.ExceptionLogHelper;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.DividendYield;
import org.jsoup.nodes.Element;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverDividendYieldParser {

    private static final String HEADER_TEXT = "시가배당률(%)";
    private static final int ELEMENT_INDEX = 14;

    public static DividendYield dividendYield(Period period, Element performanceTable) {
        try {
            String textValue = NaverPerformanceParseHelper.element(period, performanceTable,
                                                                   ELEMENT_INDEX, HEADER_TEXT);
            return new DividendYield(Double.valueOf(textValue));
        } catch (Exception e) {
            ExceptionLogHelper.logParseException(NaverDividendYieldParser.class.getSimpleName(), e);
            return DividendYield.UNKNOWN_VALUE;
        }
    }
}
