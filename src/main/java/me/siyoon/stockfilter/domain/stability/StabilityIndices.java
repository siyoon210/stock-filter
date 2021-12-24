package me.siyoon.stockfilter.domain.stability;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import me.siyoon.stockfilter.domain.Period;

@Builder
@AllArgsConstructor
public class StabilityIndices { // 기간별 안정성 지표

    private final Map<Period, StabilityIndex> value;

    public List<StabilityIndex> in(List<Period> periods) {
        return periods.stream()
                      .map(period -> value.getOrDefault(period, StabilityIndex.UNKNOWN_VALUE))
                      .collect(Collectors.toList());
    }

    public StabilityIndex of(Period period) {
        return value.getOrDefault(period, StabilityIndex.UNKNOWN_VALUE);
    }

    @Override
    public String toString() {
        return "기간별 안정성 지표: [\n {" +
                value.entrySet().stream()
                     .map(entry -> entry.getKey().description + " : " + entry.getValue())
                     .collect(Collectors.joining("}, \n {")) +
                "}\n]";
    }
}
