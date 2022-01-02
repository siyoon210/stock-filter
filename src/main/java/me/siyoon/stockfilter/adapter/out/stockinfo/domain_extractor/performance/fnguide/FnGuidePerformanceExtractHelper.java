package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide;

import java.util.EnumMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.exception.StockInfoErrorException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FnGuidePerformanceExtractHelper {

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

    public static Element element(CrawledData crawledData, Period period,
                                  FnGuideFinanceTableExtractParam extractParam) {
        Element tableBody = tableBody(crawledData.fnGuideMainPage, period);
        validateLabel(tableBody, extractParam);
        return tableBody.getElementsByTag("tr").get(extractParam.elementIndex)
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

        throw new StockInfoErrorException("Unknown Period : " + period);
    }

    private static void validateLabel(Element tableBody,
                                      FnGuideFinanceTableExtractParam extractParam) {
        String extractedLabel = extractedLabel(tableBody, extractParam.elementIndex);
        if (!extractParam.labels.contains(extractedLabel)) {
            log.warn("validateHeaderText 에러 {} not contains {}",
                     extractParam.labels, extractedLabel);
            throw new StockInfoErrorException();
        }
    }

    private static String extractedLabel(Element tableBody, int elementIndex) {
        Element th = tableBody.getElementsByTag("tr").get(elementIndex)
                              .getElementsByTag("th").get(0);
        if (th.getElementsByTag("dl").isEmpty()) {
            return th.text();
        }
        return th.getElementsByTag("dl").get(0)
                 .getElementsByTag("dt").get(0).text();
    }
}
