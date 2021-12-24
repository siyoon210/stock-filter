package me.siyoon.stockfilter.domain.performance;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DPS { // 주당 배당금 (Dividend Per Share)

    public static final DPS UNKNOWN_VALUE = new DPS(Double.MIN_VALUE);

    private final Double value;

    public DividendYield expectedDividendYield(Double price) { // 예상 배당률
        return new DividendYield(value / price * 100);
    }
}
