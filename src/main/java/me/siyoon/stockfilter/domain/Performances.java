package me.siyoon.stockfilter.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class Performances { // 기간별 실적 분석

    private final Map<Period, Performance> value;

    public List<Performance> in(List<Period> periods) {
        return periods.stream()
                      .map(period -> value.getOrDefault(period, Performance.UNKNOWN_VALUE))
                      .collect(Collectors.toList());
    }

    public Performance of(Period period) {
        return value.getOrDefault(period, Performance.UNKNOWN_VALUE);
    }
}