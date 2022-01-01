package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide.FnGuideFinanceTableExtractParam;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide.FnGuidePerformanceExtractHelper;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.DividendYield;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.PerformanceExceptionHandler.handle;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DividendYieldExtractor {

    private static final FnGuideFinanceTableExtractParam EXTRACT_PARAM;

    static {
        EXTRACT_PARAM = FnGuideFinanceTableExtractParam.builder()
                                                       .labels("배당수익률(%)")
                                                       .elementIndex(24)
                                                       .build();
    }

    public static DividendYield dividendYield(CrawledData crawledData, Period period) {
        try {
            Element element = FnGuidePerformanceExtractHelper.element(crawledData, period,
                                                                      EXTRACT_PARAM);
            return DividendYield.from(doubleValue(element.text()));
        } catch (RuntimeException e) {
            return (DividendYield) handle(e, crawledData.stockCode, period,
                                          DividendYield.UNKNOWN_VALUE);
        }
    }
}
