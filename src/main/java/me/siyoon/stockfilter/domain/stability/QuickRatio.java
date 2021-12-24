package me.siyoon.stockfilter.domain.stability;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class QuickRatio { // 당좌비율

    public static final QuickRatio UNKNOWN_VALUE = new QuickRatio(Double.MIN_VALUE);

    private final Double value;

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }
}
