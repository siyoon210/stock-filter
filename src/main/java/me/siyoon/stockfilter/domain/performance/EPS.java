package me.siyoon.stockfilter.domain.performance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class EPS implements Serializable { // 주당순이익 (Earning Per Share)

    public static final EPS UNKNOWN_VALUE = new EPS(Double.MIN_VALUE);

    private final Double value;

    public static EPS from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }
        return new EPS(value);
    }

    public PER calculatedPer(Double price) {
        if (this.equals(UNKNOWN_VALUE) || price == null) {
            return PER.UNKNOWN_VALUE;
        }
        return PER.from(price / value);
    }

    public Double increaseRate(EPS targetEps) {
        if (this.value == 0.0) {
            return Double.MIN_VALUE;
        }
        return BigDecimal.valueOf(targetEps.value)
                         .divide(BigDecimal.valueOf(this.value), 2, RoundingMode.HALF_UP)
                         .subtract(BigDecimal.ONE)
                         .multiply(BigDecimal.valueOf(100))
                         .doubleValue();
    }

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }

    public boolean isNegative() {
        return value < 0;
    }

    public boolean isZero() {
        return value == 0;
    }

    public boolean isPositive() {
        return value > 0;
    }
}
