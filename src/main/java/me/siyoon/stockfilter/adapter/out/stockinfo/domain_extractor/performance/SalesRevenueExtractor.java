package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide.FnGuideFinanceTableExtractParam;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide.FnGuidePerformanceExtractHelper;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.SalesRevenue;
import me.siyoon.stockfilter.exception.StockInfoFatalException;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SalesRevenueExtractor {

    private static final FnGuideFinanceTableExtractParam EXTRACT_PARAM;

    static {
        EXTRACT_PARAM = FnGuideFinanceTableExtractParam.builder()
                                                       .label("매출액")
                                                       .elementIndex(0)
                                                       .build();
    }

    public static SalesRevenue salesRevenue(CrawledData crawledData, Period period) {
        try {
            Element element = FnGuidePerformanceExtractHelper.element(crawledData, period,
                                                                      EXTRACT_PARAM);
            return SalesRevenue.from(doubleValue(element.text()));
        } catch (StockInfoFatalException e) {
            throw e;
        } catch (NullPointerException e) {
            log.info("SalesRevenueExtractor. NPE stockCode: {}, period: {}",
                     crawledData.stockCode, period);
            return SalesRevenue.UNKNOWN_VALUE;
        }catch (RuntimeException e) {
            log.warn("SalesRevenueExtractor. stockCode: {}, period: {}",
                     crawledData.stockCode, period, e);
            return SalesRevenue.UNKNOWN_VALUE;
        }
    }
}
