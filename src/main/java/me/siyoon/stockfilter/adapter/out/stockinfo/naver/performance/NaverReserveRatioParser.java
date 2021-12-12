package me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.ExceptionLogHelper;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.ReserveRatio;
import org.jsoup.nodes.Element;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverReserveRatioParser {

    private static final String HEADER_TEXT = "유보율";
    private static final int ELEMENT_INDEX = 8;

    public static ReserveRatio reserveRatio(Period period, Element performanceTable) {
        try {
            String textValue = NaverPerformanceParseHelper.element(period, performanceTable,
                                                                   ELEMENT_INDEX, HEADER_TEXT);
            return new ReserveRatio(Double.valueOf(textValue));
        } catch (Exception e) {
            ExceptionLogHelper.logParseException(NaverReserveRatioParser.class.getSimpleName(), e);
            return ReserveRatio.UNKNOWN_VALUE;
        }
    }
}
