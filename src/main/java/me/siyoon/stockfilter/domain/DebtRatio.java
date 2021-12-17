package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DebtRatio { // 부채비율

    public static final DebtRatio UNKNOWN_VALUE = new DebtRatio(Double.MAX_VALUE);

    private final Double value;

    public boolean isLessThan(Double value) {
        return this.value < value;
    }
}
