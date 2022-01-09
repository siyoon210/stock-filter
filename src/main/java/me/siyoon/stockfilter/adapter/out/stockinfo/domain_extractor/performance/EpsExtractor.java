package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.naver.fin_summary.NaverFinSummaryExtractHelper;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.naver.fin_summary.NaverFinSummaryExtractParam;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.EPS;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.PerformanceExceptionHandler.handle;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EpsExtractor {

    private static final NaverFinSummaryExtractParam EXTRACT_PARAM;

    static {
        EXTRACT_PARAM = NaverFinSummaryExtractParam.builder()
                                                   .expectedLabel("EPS(원)")
                                                   .elementIndex(25)
                                                   .build();
    }

    public static EPS eps(CrawledData crawledData, Period period) {
        try {
            Element element = NaverFinSummaryExtractHelper.element(crawledData, period,
                                                                   EXTRACT_PARAM);
            return EPS.from(doubleValue(element.text()));
        } catch (RuntimeException e) {
            return (EPS) handle(e, crawledData.stockCode, period,
                                         EPS.UNKNOWN_VALUE);
        }
    }
}
