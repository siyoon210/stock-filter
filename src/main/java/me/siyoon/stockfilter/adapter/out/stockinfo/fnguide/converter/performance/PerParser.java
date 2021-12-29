package me.siyoon.stockfilter.adapter.out.stockinfo.fnguide.converter.performance;

import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.EPS;
import me.siyoon.stockfilter.exception.StockInfoFatalException;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import static me.siyoon.stockfilter.adapter.out.stockinfo.fnguide.converter.performance.PerformanceParseHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@Component
public class PerParser {

    private static final String LABEL = "EPS(원) 지배주주순이익 / 수정평균주식수EPS(원)";
    private static final int ELEMENT_INDEX = 18;

    public EPS eps(CrawledData crawledData, Period period) {
        try {
            Element element = element(crawledData, period, LABEL, ELEMENT_INDEX);
            return EPS.from(doubleValue(element.text()));
        } catch (StockInfoFatalException e) {
            throw e;
        }  catch (RuntimeException e) {
            log.warn("EPSParser. data: {}, period: {}", crawledData, period, e);
            return EPS.UNKNOWN_VALUE;
        }
    }
}
