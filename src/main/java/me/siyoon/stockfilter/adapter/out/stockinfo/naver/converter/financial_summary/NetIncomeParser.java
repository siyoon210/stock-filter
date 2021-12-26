package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.NetIncome;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary.FinancialSummaryParseHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NetIncomeParser {

    private static final String LABEL = "당기순이익";
    private static final int ELEMENT_INDEX = 4;

    public static NetIncome netIncome(CrawledData crawledData, Period period) {
        Element financialSummary = crawledData.financialSummary(period);
        try {
            Element element = element(financialSummary, period, LABEL, ELEMENT_INDEX);
            return NetIncome.from(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("NetIncomeParser. financialSummary: {}, period: {}",
                     financialSummary, period.description);
            return NetIncome.UNKNOWN_VALUE;
        }
    }
}
