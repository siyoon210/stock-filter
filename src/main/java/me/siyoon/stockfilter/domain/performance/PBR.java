package me.siyoon.stockfilter.domain.performance;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PBR implements Serializable {

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
