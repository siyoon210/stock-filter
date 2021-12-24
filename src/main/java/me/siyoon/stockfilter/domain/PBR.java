package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.siyoon.stockfilter.domain.performance.Equity;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PBR {

    public static final PBR UNKNOWN_VALUE = new PBR(Double.MAX_VALUE);

    private final Double value;

    public static PBR pbr(Double marketCap, Equity... equities) {
        double value = marketCap / Equity.totalValue(equities);
        return new PBR(value);
    }

    public Double inverseValue() {
        return 1 / value;
    }
}
