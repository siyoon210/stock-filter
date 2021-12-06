package me.siyoon.stockfilter.adapter.out.naver.performace;

import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.exception.StockInfoFatalException;
import me.siyoon.stockfilter.exception.StockInfoParseException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverPerformanceParseHelper {

    private static final Map<Period, Integer> INDEX_BY_PERIOD = Map.of(Period.THIS_YEAR, 3,
                                                                       Period.LAST_YEAR, 2,
                                                                       Period.TWO_YEARS_AGO, 1,
                                                                       Period.THREE_YEARS_AGO, 0);
    public static Element performanceTable(Document document) {
        try {
            Element performanceTable = document.getElementById("content")
                                      .getElementsByClass("cop_analysis").get(0)
                                      .getElementsByClass("sub_section").get(0)
                                      .getElementsByTag("table").get(0);

            if (doesNotHaveAnyPerformanceInfo(performanceTable)) {
                throw new StockInfoParseException("실적 정보 없음");
            }

            return performanceTable;
        } catch (Exception e) {
            throw new StockInfoParseException("실적 분석 테이블 파싱 실패 " + e.getMessage());
        }
    }

    private static boolean doesNotHaveAnyPerformanceInfo(Element performanceTable) {
        return performanceTable.getElementsByTag("caption").isEmpty();
    }

    public static String element(Period period, Element performanceTable,
                           int elementIndex, String elementText) {
        try {
            Element element = performanceTable.getElementsByTag("tbody").get(0)
                                              .getElementsByTag("tr").get(elementIndex);
            validateHeaderText(element, elementText);
            return element.getElementsByTag("td").get(INDEX_BY_PERIOD.get(period))
                          .text().replace(",", "");
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
