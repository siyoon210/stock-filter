package me.siyoon.stockfilter.domain.performance;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class DebtRatio { // 부채비율

    public static final DebtRatio UNKNOWN_VALUE = new DebtRatio(Double.MAX_VALUE);

    private final Double value;

    public static DebtRatio from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }
        return new DebtRatio(value);
    }

    public boolean isLessThan(Double value) {
        return this.value < value;
    }
}
