package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.DividendYield;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary.FinancialSummaryParseHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DividendYieldParser {

    private static final String LABEL = "현금배당수익률";
    private static final int ELEMENT_INDEX = 30;

    public static DividendYield dividendYield(CrawledData crawledData, Period period) {
        Element financialSummary = crawledData.financialSummary(period);
        try {
            Element element = element(financialSummary, period, LABEL, ELEMENT_INDEX);
            return DividendYield.from(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("DividendYieldParser. financialSummary: {}, period: {}",
                     financialSummary, period.description);
            return DividendYield.UNKNOWN_VALUE;
        }
    }
}
