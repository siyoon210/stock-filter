package me.siyoon.stockfilter.adapter.out.naver.performace;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.NetIncome;
import me.siyoon.stockfilter.domain.Period;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.naver.ExceptionLogHelper.logParseException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverNetIncomeParser {

    public static final String NET_INCOME_TEXT = "당기순이익";
    public static final int NET_INCOME_INDEX = 2;

    public static NetIncome netIncome(Period period, Element performanceTable) {
        try {
            String textValue = NaverPerformanceParseHelper.element(period, performanceTable,
                                                                   NET_INCOME_INDEX,
                                                                   NET_INCOME_TEXT);
            return new NetIncome(Double.valueOf(textValue));
        } catch (Exception e) {
            logParseException(NaverNetIncomeParser.class.getSimpleName(), e);
            return NetIncome.UNKNOWN_VALUE;
        }
    }
}
