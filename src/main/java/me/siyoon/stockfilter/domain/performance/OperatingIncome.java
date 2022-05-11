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
public class OperatingIncome implements Serializable { // 영업이익

    public static final OperatingIncome UNKNOWN_VALUE = new OperatingIncome(Double.MIN_VALUE);

    private final Double value;

    public static OperatingIncome from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }

        return new OperatingIncome(value);
    }

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }

    public Double averageGrowthRateFrom(OperatingIncome operatingIncome, int term) {
        return (Math.pow(BigDecimal.valueOf(value)
                                   .divide(BigDecimal.valueOf(operatingIncome.value), 4,
                                                 RoundingMode.HALF_UP)
                                   .doubleValue(), (double) 1 / term) - 1) * 100;
    }
}
