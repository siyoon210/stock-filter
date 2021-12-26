package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.stability_index;

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
class StabilityIndexParseHelper {

    private static final Map<Period, Integer> INDEX_BY_PERIOD = new EnumMap<>(Period.class);

    static {
        Map<Period, Integer> indexByYears = Map.of(
                Period.THREE_YEARS_AGO, 1,
                Period.TWO_YEARS_AGO, 2,
                Period.LAST_YEAR, 3,
                Period.THIS_YEAR_EXPECTED, 4
        );
        INDEX_BY_PERIOD.putAll(indexByYears);

        Map<Period, Integer> indexByQuarters = Map.of(
                Period.FOUR_QUARTERS_AGO, 1,
                Period.THREE_QUARTERS_AGO, 2,
                Period.TWO_QUARTERS_AGO, 3,
                Period.LAST_QUARTER, 4,
                Period.NEXT_QUARTER_EXPECTED, 5
        );
        INDEX_BY_PERIOD.putAll(indexByQuarters);
    }

    public static Element element(Element stabilityIndex, Period period,
                                  String label, int elementIndex) {
        Element element = stabilityIndex.getElementsByTag("tbody").get(0)
                                        .getElementsByTag("tr").get(elementIndex);
        validateLabel(element, label);

        Integer periodIndex = INDEX_BY_PERIOD.get(period);
        return element.getElementsByTag("td").get(periodIndex);
    }

    private static void validateLabel(Element element, String label) {
        String extractedLabel = element.getElementsByClass("txt").get(0)
                                       .attr("title");
        if (!label.equals(extractedLabel)) {
            throw new StockInfoFatalException(label + " 파싱 실패. element= " + element);
        }
    }
}
