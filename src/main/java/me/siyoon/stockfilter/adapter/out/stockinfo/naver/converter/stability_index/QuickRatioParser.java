package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.stability_index;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.QuickRatio;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.stability_index.StabilityIndexParseHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuickRatioParser {

    private static final String LABEL = "당좌비율";
    private static final int ELEMENT_INDEX = 5;

    public static QuickRatio quickRatio(CrawledData crawledData, Period period) {
        Element stabilityIndex = crawledData.stabilityIndex(period);
        try {
            Element element = element(stabilityIndex, period, LABEL, ELEMENT_INDEX);
            return QuickRatio.from(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("QuickRatioParser. stabilityIndex: {}, period: {}",
                     stabilityIndex, period.description);
            return QuickRatio.UNKNOWN_VALUE;
        }
    }
}
