package me.siyoon.stockfilter.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import me.siyoon.stockfilter.application.port.in.Period;

@AllArgsConstructor
public class Performances { // 기간별 실적 분석

    private final Map<Period, Performance> value;

    public List<Performance> in(List<Period> periods) {
        return periods.stream()
                      .map(value::get)
                      .collect(Collectors.toList());
    }
}
