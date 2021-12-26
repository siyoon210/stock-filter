package me.siyoon.stockfilter.domain.performance;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class DPS { // 주당 배당금 (Dividend Per Share)

    public static final DPS UNKNOWN_VALUE = new DPS(Double.MIN_VALUE);

    private final Double value;

    public static DPS from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }

        return new DPS(value);
    }

    public DividendYield expectedDividendYield(Double price) { // 예상 배당률
        return DividendYield.from(value / price * 100);
    }
}
