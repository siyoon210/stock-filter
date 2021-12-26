package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.PER;
import me.siyoon.stockfilter.domain.Period;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.financial_summary.FinancialSummaryParseHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerParser {

    private static final String LABEL = "PER(배)";
    private static final int ELEMENT_INDEX = 26;

    public static PER per(CrawledData crawledData, Period period) {
        Element financialSummary = crawledData.financialSummary(period);
        try {
            Element element = element(financialSummary, period, LABEL, ELEMENT_INDEX);
            return PER.from(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("PerParser. financialSummary: {}, period: {}",
                     financialSummary, period.description);
            return PER.UNKNOWN_VALUE;
        }
    }
}
