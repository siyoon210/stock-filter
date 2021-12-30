package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide.FnGuideFinanceTableExtractParam;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide.FnGuidePerformanceExtractHelper;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.EPS;
import me.siyoon.stockfilter.exception.StockInfoFatalException;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EpsExtractor {

    private static final FnGuideFinanceTableExtractParam EXTRACT_PARAM;

    static {
        EXTRACT_PARAM = FnGuideFinanceTableExtractParam.builder()
                                                       .label("EPS(원) 지배주주순이익 / 수정평균주식수EPS(원)")
                                                       .elementIndex(18)
                                                       .build();
    }

    public static EPS eps(CrawledData crawledData, Period period) {
        try {
            Element element = FnGuidePerformanceExtractHelper.element(crawledData, period,
                                                                      EXTRACT_PARAM);
            return EPS.from(doubleValue(element.text()));
        } catch (StockInfoFatalException e) {
            throw e;
        } catch (NullPointerException e) {
            log.info("EPSExtractor. NPE stockCode: {}, period: {}",
                     crawledData.stockCode, period);
            return EPS.UNKNOWN_VALUE;
        } catch (RuntimeException e) {
            log.warn("EpsExtractor. stockCode: {}, period: {}", crawledData.stockCode, period, e);
            return EPS.UNKNOWN_VALUE;
        }
    }
}
