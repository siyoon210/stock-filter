package me.siyoon.stockfilter.domain.performance;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import me.siyoon.stockfilter.domain.Period;

@Builder
@AllArgsConstructor
public class Performances implements Serializable { // 기간별 기업 재무실적

    private final Map<Period, Performance> value;

    public List<Performance> in(List<Period> periods) {
        return periods.stream()
                      .map(period -> value.getOrDefault(period, Performance.UNKNOWN_VALUE))
                      .collect(Collectors.toList());
    }

    public Performance of(Period period) {
        return value.getOrDefault(period, Performance.UNKNOWN_VALUE);
    }

    @Override
    public String toString() {
        return "기간별 기업 재무 실적: [\n {" +
                value.entrySet().stream()
                     .map(entry -> entry.getKey().description + " : " + entry.getValue())
                     .collect(Collectors.joining("}, \n {")) +
                "}\n]";
    }
}
