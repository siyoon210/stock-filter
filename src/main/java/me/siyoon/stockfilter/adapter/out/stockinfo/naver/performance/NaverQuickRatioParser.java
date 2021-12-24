package me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.ExceptionLogHelper;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.stability.QuickRatio;
import org.jsoup.nodes.Element;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverQuickRatioParser {

    private static final String HEADER_TEXT = "당좌비율";
    private static final int ELEMENT_INDEX = 7;

    public static QuickRatio quickRatio(Period period, Element performanceTable) {
        try {
            String textValue = NaverPerformanceParseHelper.element(period, performanceTable,
                                                                   ELEMENT_INDEX, HEADER_TEXT);
            return new QuickRatio(Double.valueOf(textValue));
        } catch (Exception e) {
            ExceptionLogHelper.logParseException(NaverQuickRatioParser.class.getSimpleName(), e);
            return QuickRatio.UNKNOWN_VALUE;
        }
    }
}
