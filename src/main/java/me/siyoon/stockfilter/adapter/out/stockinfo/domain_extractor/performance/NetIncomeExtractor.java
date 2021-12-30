package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide.FnGuideFinanceTableExtractParam;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide.FnGuidePerformanceExtractHelper;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.NetIncome;
import me.siyoon.stockfilter.exception.StockInfoFatalException;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NetIncomeExtractor {

    private static final FnGuideFinanceTableExtractParam EXTRACT_PARAM;

    static {
        EXTRACT_PARAM = FnGuideFinanceTableExtractParam.builder()
                                                       .label("당기순이익")
                                                       .elementIndex(3)
                                                       .build();
    }

    public static NetIncome netIncome(CrawledData crawledData, Period period) {
        try {
            Element element = FnGuidePerformanceExtractHelper.element(crawledData, period,
                                                                      EXTRACT_PARAM);
            return NetIncome.from(doubleValue(element.text()));
        } catch (StockInfoFatalException e) {
            throw e;
        } catch (NullPointerException e) {
            log.info("NetIncomeExtractor. NPE stockCode: {}, period: {}",
                     crawledData.stockCode, period);
            return NetIncome.UNKNOWN_VALUE;
        } catch (RuntimeException e) {
            log.warn("NetIncomeExtractor. stockCode: {}, period: {}", crawledData.stockCode, period,
                     e);
            return NetIncome.UNKNOWN_VALUE;
        }
    }
}
