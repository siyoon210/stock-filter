package me.siyoon.stockfilter.adapter.out.naver;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.naver.performace.NaverNetIncomeParser;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.domain.Performance;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.exception.StockInfoParseException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NaverPerformanceParser {

    private final NaverNetIncomeParser netIncomeParser;

    public Map<Period, Performance> performances(StockFilterCommand filterCommand,
                                                 Document document) {
        List<Period> periods = filterCommand.netIncome.periods;
        Element performanceTable = performanceTable(document);

        return periods.stream()
                      .collect(Collectors.toMap(period -> period,
                                                period -> performance(period, performanceTable)));
    }

    private Performance performance(Period period, Element performanceTable) {

        return Performance.builder()
                          .netIncome(netIncomeParser.netIncome(period, performanceTable))
                          .build();
    }

    private Element performanceTable(Document document) {
        try {
            return document.getElementById("content")
                           .getElementsByClass("cop_analysis").get(0)
                           .getElementsByClass("sub_section").get(0)
                           .getElementsByTag("table").get(0);
        } catch (Exception e) {
            throw new StockInfoParseException("실적 분석 테이블 파싱 실패 " + e.getMessage());
        }
    }
}
