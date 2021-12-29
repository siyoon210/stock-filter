package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.main_performance;

import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.exception.StockInfoFatalException;
import me.siyoon.stockfilter.exception.StockInfoParseException;
import org.jsoup.nodes.Element;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MainPerformanceParseHelper {

    private static final Map<Period, Integer> INDEX_BY_PERIOD = Map.of(Period.THREE_YEARS_AGO, 0,
                                                                       Period.TWO_YEARS_AGO, 1,
                                                                       Period.LAST_YEAR, 2,
                                                                       Period.THIS_YEAR_EXPECTED, 3,
                                                                       Period.FOUR_QUARTERS_AGO, 4,
                                                                       Period.THREE_QUARTERS_AGO, 5,
                                                                       Period.TWO_QUARTERS_AGO, 6,
                                                                       Period.LAST_QUARTER, 7,
                                                                       Period.THIS_QUARTER_EXPECTED,
                                                                       8);

    public static Element element(Element performanceTable, Period period,
                                 String elementText, int elementIndex) {
        try {
            Element element = performanceTable.getElementsByTag("tbody").get(0)
                                              .getElementsByTag("tr").get(elementIndex);
            validateHeaderText(element, elementText);
            return element.getElementsByTag("td").get(INDEX_BY_PERIOD.get(period));
        } catch (Exception e) {
            throw new StockInfoParseException(elementText + " 파싱 실패 " + e.getMessage());
        }
    }

    private static void validateHeaderText(Element element, String elementText) {
        String actualText = element.getElementsByTag("th").get(0).text();
        if (!elementText.equals(actualText)) {
            throw new StockInfoFatalException(
                    elementText + " is not equal to expected header text. text = " + actualText);
        }
    }
}
