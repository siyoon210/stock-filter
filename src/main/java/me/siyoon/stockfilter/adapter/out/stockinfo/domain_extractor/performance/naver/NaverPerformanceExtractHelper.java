package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.naver;

import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.exception.StockInfoFatalException;
import org.jsoup.nodes.Element;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverPerformanceExtractHelper {

    private static final Map<Period, Integer> INDEX_BY_PERIOD
            = Map.of(Period.THREE_YEARS_AGO, 0,
                     Period.TWO_YEARS_AGO, 1,
                     Period.LAST_YEAR, 2,
                     Period.THIS_YEAR_EXPECTED, 3,
                     Period.FOUR_QUARTERS_AGO, 5,
                     Period.THREE_QUARTERS_AGO, 6,
                     Period.TWO_QUARTERS_AGO, 7,
                     Period.LAST_QUARTER, 8,
                     Period.THIS_QUARTER_EXPECTED, 9);

    public static Element element(CrawledData crawledData, Period period,
                                  NaverCompanyStateTableExtractParam EXTRACT_PARAM) {
        Element element = crawledData.naverPerformanceTable()
                                     .getElementsByTag("tbody").get(0)
                                     .getElementsByTag("tr").get(EXTRACT_PARAM.elementIndex);
        validateHeaderText(element, EXTRACT_PARAM.label);
        return element.getElementsByTag("td").get(INDEX_BY_PERIOD.get(period));
    }

    private static void validateHeaderText(Element element, String elementText) {
        String actualText = element.getElementsByTag("th").get(0).text();
        if (!elementText.equals(actualText)) {
            throw new StockInfoFatalException(
                    elementText + " is not equal to expected header text. text = " + actualText);
        }
    }
}
