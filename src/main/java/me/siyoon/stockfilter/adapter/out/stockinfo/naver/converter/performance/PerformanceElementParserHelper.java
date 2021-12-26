package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance;

import java.util.EnumMap;
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

    private static final Map<Period, Integer> INDEX_BY_PERIOD = new EnumMap<>(Period.class);

    static {
        Map<Period, Integer> indexByYears = Map.of(
                Period.THREE_YEARS_AGO, 2,
                Period.TWO_YEARS_AGO, 3,
                Period.LAST_YEAR, 4,
                Period.THIS_YEAR_EXPECTED, 5,
                Period.NEXT_YEAR_EXPECTED, 6
        );
        INDEX_BY_PERIOD.putAll(indexByYears);

        Map<Period, Integer> indexByQuarters = Map.of(
                Period.FOUR_QUARTERS_AGO, 1,
                Period.THREE_QUARTERS_AGO, 2,
                Period.TWO_QUARTERS_AGO, 3,
                Period.LAST_QUARTER, 4,
                Period.NEXT_QUARTER_EXPECTED, 5,
                Period.AFTER_TWO_QUARTERS_EXPECTED, 6,
                Period.AFTER_THREE_QUARTERS_EXPECTED, 7
        );
        INDEX_BY_PERIOD.putAll(indexByQuarters);
    }

    public static Element element(Element performanceTable, Period period,
                                  String label, int elementIndex) {
        Element element = performanceTable.getElementsByTag("tbody").get(0)
                                          .getElementsByTag("tr").get(elementIndex);
        validateLabel(element, label);

        Integer periodIndex = INDEX_BY_PERIOD.get(period);
        return element.getElementsByTag("td").get(periodIndex);
    }

    private static void validateLabel(Element element, String label) {
        String extractedLabel = element.getElementsByTag("th").get(0).text();
        if (!label.equals(extractedLabel)) {
            throw new StockInfoFatalException(label + " 파싱 실패. element= " + element);
        }
    }
}
