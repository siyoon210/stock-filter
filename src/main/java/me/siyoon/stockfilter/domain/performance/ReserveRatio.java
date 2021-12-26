package me.siyoon.stockfilter.domain.performance;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ReserveRatio { // 유보율

    public static final ReserveRatio UNKNOWN_VALUE = new ReserveRatio(Double.MIN_VALUE);

    private final Double value;

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }
}
