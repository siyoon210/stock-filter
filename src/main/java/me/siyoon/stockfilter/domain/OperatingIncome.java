package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OperatingIncome { // 영업이익

    public static final OperatingIncome UNKNOWN_VALUE = new OperatingIncome(Double.MIN_VALUE);

    private final Double value;

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }
}
