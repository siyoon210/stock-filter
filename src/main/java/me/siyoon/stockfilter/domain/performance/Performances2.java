package me.siyoon.stockfilter.domain.performance;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import me.siyoon.stockfilter.domain.Period;

@Builder
@AllArgsConstructor
public class Performances2 { // 기간별 기업 재무실적

    private final Map<Period, Performance2> value;

    public List<Performance2> in(List<Period> periods) {
        return periods.stream()
                      .map(period -> value.getOrDefault(period, Performance2.UNKNOWN_VALUE))
                      .collect(Collectors.toList());
    }

    public Performance2 of(Period period) {
        return value.getOrDefault(period, Performance2.UNKNOWN_VALUE);
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
