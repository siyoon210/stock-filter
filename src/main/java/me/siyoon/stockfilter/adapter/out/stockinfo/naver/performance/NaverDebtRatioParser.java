package me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.ExceptionLogHelper;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.stability.DebtRatio;
import org.jsoup.nodes.Element;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverDebtRatioParser {

    private static final String DEBT_RATIO_TEXT = "부채비율";
    private static final int DEBT_RATIO_INDEX = 6;

    public static DebtRatio debtRatio(Period period, Element performanceTable) {
        try {
            String textValue = NaverPerformanceParseHelper.element(period, performanceTable,
                                                                   DEBT_RATIO_INDEX, DEBT_RATIO_TEXT);
            return new DebtRatio(Double.valueOf(textValue));
        } catch (Exception e) {
            ExceptionLogHelper.logParseException(NaverDebtRatioParser.class.getSimpleName(), e);
            return DebtRatio.UNKNOWN_VALUE;
        }
    }
}
