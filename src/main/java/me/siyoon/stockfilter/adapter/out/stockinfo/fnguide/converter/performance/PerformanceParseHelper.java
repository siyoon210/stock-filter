package me.siyoon.stockfilter.adapter.out.stockinfo.fnguide.converter.performance;

import java.util.EnumMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.exception.StockInfoFatalException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerformanceParseHelper {

    private static final Map<Period, Integer> INDEX_OF_PERIOD = new EnumMap<>(Period.class);

    static {
        Map<Period, Integer> indexByYears = Map.of(
                Period.THREE_YEARS_AGO, 2,
                Period.TWO_YEARS_AGO, 3,
                Period.LAST_YEAR, 4,
                Period.THIS_YEAR_EXPECTED, 5,
                Period.NEXT_YEAR_EXPECTED, 6
        );
        INDEX_OF_PERIOD.putAll(indexByYears);

        Map<Period, Integer> indexByQuarters = Map.of(
                Period.FOUR_QUARTERS_AGO, 1,
                Period.THREE_QUARTERS_AGO, 2,
                Period.TWO_QUARTERS_AGO, 3,
                Period.LAST_QUARTER, 4,
                Period.THIS_QUARTER_EXPECTED, 5,
                Period.NEXT_QUARTER_EXPECTED, 6,
                Period.AFTER_TWO_QUARTERS_EXPECTED, 7
        );
        INDEX_OF_PERIOD.putAll(indexByQuarters);
    }

    public static Element element(CrawledData crawledData, Period period, String label,
                                  int elementIndex) {
        Element tableBody = tableBody(crawledData.mainPage, period);
        validateLabel(tableBody, label, elementIndex);
        return tableBody.getElementsByTag("tr").get(elementIndex)
                        .getElementsByTag("td").get(INDEX_OF_PERIOD.get(period));
    }

    private static Element tableBody(Document mainPage, Period period) {
        return mainPage.getElementById(tableElementId(period))
                       .getElementsByTag("table").get(0)
                       .getElementsByTag("tbody").get(0);
    }

    private static String tableElementId(Period period) {
        if (Period.YEARS.contains(period)) {
            return "highlight_D_Y";
        }

        if (Period.QUARTERS.contains(period)) {
            return "highlight_D_Q";
        }

        throw new StockInfoFatalException("Unknown Period : " + period);
    }

    private static void validateLabel(Element tableBody, String label, int elementIndex) {
        String extractedLabel = extractedLabel(tableBody, elementIndex);
        if (!label.equals(extractedLabel)) {
            throw new StockInfoFatalException(label + " 파싱 실패. element: " + tableBody);
        }
    }

    private static String extractedLabel(Element tableBody, int elementIndex) {
        return tableBody.getElementsByTag("tr").get(elementIndex)
                        .getElementsByTag("th").get(0).text();
    }
}
