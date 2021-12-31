package me.siyoon.stockfilter.domain.performance;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PER implements Serializable {

    public static final PER UNKNOWN_VALUE = new PER(Double.MAX_VALUE);

    private final Double value;

    public static PER from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }
        return new PER(value);
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

    public Double improvedRatioComparedTo(PER previousPer) {
        return (previousPer.value - this.value) / previousPer.value * 100;
    }

    public static PER per(Double marketCap, NetIncome... netIncomes) {
        double value = marketCap / NetIncome.totalValue(netIncomes);
        return new PER(value);
    }

    public Double inverseValue() {
        return 1 / value;
    }
}
