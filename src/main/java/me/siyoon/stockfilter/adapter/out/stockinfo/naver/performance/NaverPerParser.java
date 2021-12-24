package me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.ExceptionLogHelper;
import me.siyoon.stockfilter.domain.PER;
import me.siyoon.stockfilter.domain.Period;
import org.jsoup.nodes.Element;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverPerParser {

    private static final String HEADER_TEXT = "PER(배)";
    private static final int ELEMENT_INDEX = 10;

    public static PER per(Period period, Element performanceTable) {
        try {
            String textValue = NaverPerformanceParseHelper.element(period, performanceTable,
                                                                   ELEMENT_INDEX, HEADER_TEXT);
            return new PER(Double.valueOf(textValue));
        } catch (Exception e) {
            ExceptionLogHelper.logParseException(NaverPerParser.class.getSimpleName(), e);
            return PER.UNKNOWN_VALUE;
        }
    }
}
