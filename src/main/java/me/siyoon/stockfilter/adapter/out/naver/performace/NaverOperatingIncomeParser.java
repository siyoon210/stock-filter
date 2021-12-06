package me.siyoon.stockfilter.adapter.out.naver.performace;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.OperatingIncome;
import me.siyoon.stockfilter.domain.Period;
import org.jsoup.nodes.Element;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverOperatingIncomeParser {

    public static final String OPERATING_INCOME_TEXT = "영업이익";
    public static final int OPERATING_INCOME_INDEX = 1;

    public static OperatingIncome operatingIncome(Period period, Element performanceTable) {
        try {
            String textValue = NaverPerformanceParseHelper.element(period, performanceTable,
                                                                   OPERATING_INCOME_INDEX,
                                                                   OPERATING_INCOME_TEXT);
            return new OperatingIncome(Double.valueOf(textValue));
        } catch (Exception e) {
            log.warn("NaverOperatingIncomeParser {}", e.getMessage());
            return OperatingIncome.UNKNOWN_VALUE;
        }
    }
}
