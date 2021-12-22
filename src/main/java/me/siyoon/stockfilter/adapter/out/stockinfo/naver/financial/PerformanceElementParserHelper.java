package me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial;

import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.exception.StockInfoFatalException;
import org.jsoup.nodes.Element;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class PerformanceElementParserHelper {

    private static final Map<Period, Integer> INDEX_BY_QUARTER_PERIOD
            = Map.of(Period.FOUR_QUARTERS_AGO, 1,
                     Period.THREE_QUARTERS_AGO, 2,
                     Period.TWO_QUARTERS_AGO, 3,
                     Period.LAST_QUARTER, 4,
                     Period.NEXT_QUARTER_EXPECTED_INDEX, 5,
                     Period.AFTER_TWO_QUARTERS_EXPECTED_INDEX, 6,
                     Period.AFTER_THREE_QUARTERS_EXPECTED_INDEX, 7);

    public static Element element(Element performanceTable, Period period,
                                  String label, int elementIndex) {
        Element element = performanceTable.getElementsByTag("tbody").get(0)
                                          .getElementsByTag("tr").get(elementIndex);
        validateLabel(element, label);

        Integer periodIndex = INDEX_BY_QUARTER_PERIOD.get(period);
        return element.getElementsByTag("td").get(periodIndex);
    }

    private static void validateLabel(Element element, String label) {
        String extractedLabel = element.getElementsByTag("th").get(0).text();
        if (!label.equals(extractedLabel)) {
            throw new StockInfoFatalException(label + " 파싱 실패. element= " + element);
        }
    }
}
