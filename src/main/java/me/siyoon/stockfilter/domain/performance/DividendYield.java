package me.siyoon.stockfilter.domain.performance;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DividendYield implements Serializable { // 배당률

    public static final DividendYield UNKNOWN_VALUE = new DividendYield(Double.MIN_VALUE);

    private final Double value;

    public static DividendYield from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }
        return new DividendYield(value);
    }

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }
}
