package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide.FnGuideFinanceTableExtractParam;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide.FnGuidePerformanceExtractHelper;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.ReserveRatio;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.PerformanceElementExceptionHandler.handle;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReserveRatioExtractor {

    private static final FnGuideFinanceTableExtractParam EXTRACT_PARAM;

    static {
        EXTRACT_PARAM = FnGuideFinanceTableExtractParam.builder()
                                                       .label("유보율(%) (유보액 / 자본금) * 100유보율")
                                                       .elementIndex(13)
                                                       .build();
    }

    public static ReserveRatio reserveRatio(CrawledData crawledData, Period period) {
        try {
            Element element = FnGuidePerformanceExtractHelper.element(crawledData, period,
                                                                      EXTRACT_PARAM);
            return ReserveRatio.from(doubleValue(element.text()));
        } catch (RuntimeException e) {
            return (ReserveRatio) handle(e, crawledData.stockCode, period,
                                         ReserveRatio.UNKNOWN_VALUE);
        }
    }
}
