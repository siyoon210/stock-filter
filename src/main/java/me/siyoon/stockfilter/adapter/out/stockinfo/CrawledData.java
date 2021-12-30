package me.siyoon.stockfilter.adapter.out.stockinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import me.siyoon.stockfilter.exception.StockInfoParseException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Builder
@AllArgsConstructor
@ToString
public class CrawledData {

    public final String stockCode;
    public final Document naverMainPage;
    public final Document fnGuideMainPage;

    public Element naverPerformanceTable() {
        try {
            Element performanceTable = naverMainPage.getElementById("content")
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

    private boolean doesNotHaveAnyPerformanceInfo(Element performanceTable) {
        return performanceTable.getElementsByTag("caption").isEmpty();
    }
}
