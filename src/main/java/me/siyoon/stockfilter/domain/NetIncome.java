package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class NetIncome { // 당기순이익

    public static final NetIncome UNKNOWN_VALUE = new NetIncome(Double.MIN_VALUE);

    private final Double value;

    public boolean isGraterThan(Double value) {
        return this.value > value;
    }
}
