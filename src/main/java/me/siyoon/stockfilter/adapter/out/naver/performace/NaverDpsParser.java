package me.siyoon.stockfilter.adapter.out.naver.performace;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.DPS;
import me.siyoon.stockfilter.domain.Period;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.naver.ExceptionLogHelper.logParseException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverDpsParser {

    private static final String DPS_TEXT = "주당배당금(원)";
    private static final int DPS_INDEX = 13;

    public static DPS dps(Period period, Element performanceTable) {
        try {
            String textValue = NaverPerformanceParseHelper.element(period, performanceTable,
                                                                   DPS_INDEX, DPS_TEXT);
            return new DPS(Double.valueOf(textValue));
        } catch (Exception e) {
            logParseException(NaverDpsParser.class.getSimpleName(), e);
            return DPS.UNKNOWN_VALUE;
        }
    }
}
