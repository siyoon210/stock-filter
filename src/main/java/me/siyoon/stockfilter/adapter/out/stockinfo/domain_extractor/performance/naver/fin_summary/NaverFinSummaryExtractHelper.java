package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.naver.fin_summary;

import java.util.EnumMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.exception.StockInfoErrorException;
import org.jsoup.nodes.Element;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverFinSummaryExtractHelper {

    private static final Map<Period, Integer> INDEX_OF_PERIOD = new EnumMap<>(Period.class);

    static {
        Map<Period, Integer> indexByYears = Map.of(
                Period.FIVE_YEARS_AGO, 0,
                Period.FOUR_YEARS_AGO, 1,
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

    private static final int ANNUAL_FIN_SUMMARY_TABLE_INDEX = 12;
    private static final int QUARTER_FIN_SUMMARY_TABLE_INDEX = 14;

    public static Element element(CrawledData crawledData, Period period,
                                  NaverFinSummaryExtractParam extractParam) {
        Element tableBody = tableBody(crawledData, period);
        validateLabel(tableBody, extractParam);
        return tableBody.getElementsByTag("tr").get(extractParam.elementIndex)
                        .getElementsByTag("td").get(INDEX_OF_PERIOD.get(period));
    }

    private static Element tableBody(CrawledData crawledData, Period period) {
        return crawledData.naverFinSummaryPage
                .document(period)
                .getElementsByTag("table").get(tableIndex(period))
                .getElementsByTag("tbody").get(0);
    }

    private static int tableIndex(Period period) {
        if (Period.YEARS.contains(period)) {
            return ANNUAL_FIN_SUMMARY_TABLE_INDEX;
        }

        if (Period.QUARTERS.contains(period)) {
            return QUARTER_FIN_SUMMARY_TABLE_INDEX;
        }

        throw new StockInfoErrorException("Invalid Period. " + period);
    }

    private static void validateLabel(Element tableBody,
                                      NaverFinSummaryExtractParam extractParam) {
        String extractedLabel = extractedLabel(tableBody, extractParam.elementIndex);
        if (!extractParam.expectedLabel.equals(extractedLabel)) {
            log.warn("validateHeaderText 에러 {} not contains {}",
                     extractParam.expectedLabel, extractedLabel);
            throw new StockInfoErrorException();
        }
    }

    private static String extractedLabel(Element tableBody, int elementIndex) {
        return tableBody.getElementsByTag("tr").get(elementIndex)
                        .getElementsByTag("th").get(0).text();
    }
}
