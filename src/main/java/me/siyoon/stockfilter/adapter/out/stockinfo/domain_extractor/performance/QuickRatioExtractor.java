package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.naver.company_state.NaverCompanyStateTableExtractParam;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.naver.company_state.NaverPerformanceExtractHelper;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.QuickRatio;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.PerformanceExceptionHandler.handle;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuickRatioExtractor {

    private static final NaverCompanyStateTableExtractParam EXTRACT_PARAM;

    static {
        EXTRACT_PARAM = NaverCompanyStateTableExtractParam.builder()
                                                          .label("당좌비율")
                                                          .elementIndex(7)
                                                          .build();
    }

    public static QuickRatio quickRatio(CrawledData crawledData, Period period) {
        try {
            Element element = NaverPerformanceExtractHelper.element(crawledData, period,
                                                                    EXTRACT_PARAM);
            return QuickRatio.from(doubleValue(element.text()));
        } catch (RuntimeException e) {
            return (QuickRatio) handle(e, crawledData.stockCode, period, QuickRatio.UNKNOWN_VALUE);
        }
    }
}
