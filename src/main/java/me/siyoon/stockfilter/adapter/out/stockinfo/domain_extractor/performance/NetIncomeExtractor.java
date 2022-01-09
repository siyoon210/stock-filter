package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.naver.fin_summary.NaverFinSummaryExtractHelper;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.naver.fin_summary.NaverFinSummaryExtractParam;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.NetIncome;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.PerformanceExceptionHandler.handle;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NetIncomeExtractor {

    private static final NaverFinSummaryExtractParam EXTRACT_PARAM;

    static {
        EXTRACT_PARAM = NaverFinSummaryExtractParam.builder()
                                                   .expectedLabel("당기순이익")
                                                   .elementIndex(4)
                                                   .build();
    }

    public static NetIncome netIncome(CrawledData crawledData, Period period) {
        try {
            Element element = NaverFinSummaryExtractHelper.element(crawledData, period,
                                                                   EXTRACT_PARAM);
            return NetIncome.from(doubleValue(element.text()));
        } catch (RuntimeException e) {
            return (NetIncome) handle(e, crawledData.stockCode, period,
                                         NetIncome.UNKNOWN_VALUE);
        }
    }
}
