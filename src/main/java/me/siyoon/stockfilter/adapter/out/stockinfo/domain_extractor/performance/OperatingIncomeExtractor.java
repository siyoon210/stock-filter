package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide.FnGuideFinanceTableExtractParam;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide.FnGuidePerformanceExtractHelper;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.OperatingIncome;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.PerformanceExceptionHandler.handle;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OperatingIncomeExtractor {

    private static final FnGuideFinanceTableExtractParam EXTRACT_PARAM;

    static {
        EXTRACT_PARAM = FnGuideFinanceTableExtractParam.builder()
                                                       .labels("영업이익")
                                                       .elementIndex(1)
                                                       .build();
    }

    public static OperatingIncome operatingIncome(CrawledData crawledData, Period period) {
        try {
            Element element = FnGuidePerformanceExtractHelper.element(crawledData, period,
                                                                      EXTRACT_PARAM);
            return OperatingIncome.from(doubleValue(element.text()));
        } catch (RuntimeException e) {
            return (OperatingIncome) handle(e, crawledData.stockCode, period,
                                            OperatingIncome.UNKNOWN_VALUE);
        }
    }
}
