package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.naver.company_state;

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
public class NaverCompanyStateExtractHelper {

    private static final Map<Period, Integer> INDEX_OF_PERIOD
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
                                  NaverCompanyStateTableExtractParam extractParam) {
        Element element = crawledData.naverMainPage().document()
                                     .getElementById("content")
                                     .getElementsByClass("cop_analysis").get(0)
                                     .getElementsByClass("sub_section").get(0)
                                     .getElementsByTag("table").get(0)
                                     .getElementsByTag("tbody").get(0)
                                     .getElementsByTag("tr").get(extractParam.elementIndex);
        validateHeaderText(element, extractParam.label);
        return element.getElementsByTag("td").get(INDEX_OF_PERIOD.get(period));
    }

    private static void validateHeaderText(Element element, String elementText) {
        String actualText = element.getElementsByTag("th").get(0).text();
        if (!elementText.equals(actualText)) {
            log.warn("validateHeaderText ?????? {} != {}", elementText, actualText);
            throw new StockInfoErrorException();
        }
    }
}
