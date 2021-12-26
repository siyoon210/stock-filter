package me.siyoon.stockfilter.domain.performance;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ReserveRatio { // 유보율

    public static final ReserveRatio UNKNOWN_VALUE = new ReserveRatio(Double.MIN_VALUE);

    private final Double value;

    public static ReserveRatio from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }
        return new ReserveRatio(value);
    }

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }
}
