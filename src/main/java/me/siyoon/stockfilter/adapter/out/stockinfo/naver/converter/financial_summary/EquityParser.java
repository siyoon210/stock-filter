package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.Equity;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary.FinancialSummaryParseHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EquityParser {

    private static final String LABEL = "자본총계";
    private static final int ELEMENT_INDEX = 9;

    public static Equity equity(CrawledData crawledData, Period period) {
        Element financialSummary = crawledData.financialSummary(period);
        try {
            Element element = element(financialSummary, period, LABEL, ELEMENT_INDEX);
            return Equity.from(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("EquityParser. financialSummary: {}, period: {}",
                     financialSummary, period.description);
            return Equity.UNKNOWN_VALUE;
        }
    }
}
