package me.siyoon.stockfilter.domain.performance;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Equity { // 자본 (순자산)

    public static final Equity UNKNOWN_VALUE = new Equity(Double.MIN_VALUE);

    private final Double value; // 억원

    public static Double totalValue(Equity... equities) {
        return Arrays.stream(equities)
                     .map(equity -> equity.value)
                     .reduce(0.0, Double::sum);
    }
}
