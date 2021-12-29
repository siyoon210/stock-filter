package me.siyoon.stockfilter.adapter.out.stockinfo.fnguide.crawler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.exception.StockInfoFatalException;
import me.siyoon.stockfilter.exception.StockInfoParseException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Builder
@AllArgsConstructor
@ToString
public class CrawledData {

    public final String stockCode;
    public final Document mainPage;
    public final Element yearlyFinancialSummary;
    public final Element quarterFinancialSummary;
    public final Element yearlyStabilityIndex;
    public final Element quarterStabilityIndex;

    public Element financialSummary(Period period) {
        if (Period.YEARS.contains(period)) {
            return yearlyFinancialSummary;
        }

        if (Period.QUARTERS.contains(period)) {
            return quarterFinancialSummary;
        }

        throw new StockInfoFatalException("Period 알 수 없음. " + period);
    }

    public Element stabilityIndex(Period period) {
        if (Period.YEARS.contains(period)) {
            return yearlyStabilityIndex;
        }

        if (Period.QUARTERS.contains(period)) {
            return quarterStabilityIndex;
        }

        throw new StockInfoFatalException("Period 알 수 없음. " + period);
    }

    public Element performanceTable() {
        try {
            Element performanceTable = mainPage.getElementById("content")
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
