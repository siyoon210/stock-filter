package me.siyoon.stockfilter.domain.performance;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class EPS { // 주당순이익 (Earning Per Share)

    public static final EPS UNKNOWN_VALUE = new EPS(Double.MIN_VALUE);

    private final Double value;

    public static EPS from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }
        return new EPS(value);
    }

    public PER calculatedPer(Double price) {
        return PER.from(price / value);
    }

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }
}
