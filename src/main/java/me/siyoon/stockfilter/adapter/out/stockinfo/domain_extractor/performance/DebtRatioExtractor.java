package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide.FnGuideFinanceTableExtractParam;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide.FnGuidePerformanceExtractHelper;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.DebtRatio;
import me.siyoon.stockfilter.exception.StockInfoFatalException;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DebtRatioExtractor {

    private static final FnGuideFinanceTableExtractParam EXTRACT_PARAM;

    static {
        EXTRACT_PARAM = FnGuideFinanceTableExtractParam.builder()
                                                       .label("부채비율(%) (총부채 / 총자본) * 100부채비율")
                                                       .elementIndex(12)
                                                       .build();
    }

    public static DebtRatio debtRatio(CrawledData crawledData, Period period) {
        try {
            Element element = FnGuidePerformanceExtractHelper.element(crawledData, period,
                                                                      EXTRACT_PARAM);
            return DebtRatio.from(doubleValue(element.text()));
        } catch (StockInfoFatalException e) {
            throw e;
        } catch (NullPointerException e) {
            log.info("DebtRatioExtractor. NPE stockCode: {}, period: {}",
                     crawledData.stockCode, period);
            return DebtRatio.UNKNOWN_VALUE;
        } catch (RuntimeException e) {
            log.warn("DebtRatioExtractor. stockCode: {}, period: {}", crawledData.stockCode, period, e);
            return DebtRatio.UNKNOWN_VALUE;
        }
    }
}
