package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.stability_index;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.DebtRatio;
import org.jsoup.nodes.Element;

import static me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.stability_index.StabilityIndexParseHelper.element;
import static me.siyoon.stockfilter.adapter.out.util.NumberExtractor.doubleValue;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DebtRatioParser {

    private static final String LABEL = "부채비율";
    private static final int ELEMENT_INDEX = 0;

    public static DebtRatio debtRatio(CrawledData crawledData, Period period) {
        Element stabilityIndex = crawledData.stabilityIndex(period);
        try {
            Element element = element(stabilityIndex, period, LABEL, ELEMENT_INDEX);
            return DebtRatio.from(doubleValue(element.text()));
        } catch (Exception e) {
            log.warn("DebtRatioParser. stabilityIndex: {}, period: {}",
                     stabilityIndex, period.description);
            return DebtRatio.UNKNOWN_VALUE;
        }
    }
}
