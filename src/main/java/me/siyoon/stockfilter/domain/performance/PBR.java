package me.siyoon.stockfilter.domain.performance;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PBR implements Serializable {

    public static final PBR UNKNOWN_VALUE = new PBR(Double.MAX_VALUE);

    private final Double value;

    public static PBR from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }
        return new PBR(value);
    }

    public boolean isLessThan(Double value) {
        if (isNegative()) {
            return false;
        }
        return this.value < value;
    }

    public boolean isPositive() {
        return this.value > 0;
    }

    public boolean isNegative() {
        return this.value < 0;
    }

    public static PBR pbr(Double marketCap, Equity... equities) {
        double value = marketCap / Equity.totalValue(equities);
        return new PBR(value);
    }

    public Double inverseValue() {
        return 1 / value;
    }
}
