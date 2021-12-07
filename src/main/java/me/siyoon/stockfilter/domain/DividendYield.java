package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class DividendYield { // 배당률

    public static final DividendYield UNKNOWN_VALUE = new DividendYield(Double.MIN_VALUE);

    private final Double value;

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }
}
