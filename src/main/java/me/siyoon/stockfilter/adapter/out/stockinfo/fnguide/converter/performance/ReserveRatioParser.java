package me.siyoon.stockfilter.adapter.out.stockinfo.fnguide.converter.performance;

import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.ReserveRatio;
import me.siyoon.stockfilter.exception.StockInfoFatalException;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import static me.siyoon.stockfilter.adapter.out.stockinfo.fnguide.converter.performance.PerformanceParseHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@Component
public class ReserveRatioParser {

    private static final String LABEL = "유보율(%) (유보액 / 자본금) * 100유보율";
    private static final int ELEMENT_INDEX = 13;

    public ReserveRatio reserveRatio(CrawledData crawledData, Period period) {
        try {
            Element element = element(crawledData, period, LABEL, ELEMENT_INDEX);
            return ReserveRatio.from(doubleValue(element.text()));
        } catch (StockInfoFatalException e) {
            throw e;
        }  catch (RuntimeException e) {
            log.warn("ReserveRatioParser. data: {}, period: {}", crawledData, period, e);
            return ReserveRatio.UNKNOWN_VALUE;
        }
    }
}
