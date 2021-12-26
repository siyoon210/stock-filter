package me.siyoon.stockfilter.domain.performance;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class QuickRatio { // 당좌비율

    public static final QuickRatio UNKNOWN_VALUE = new QuickRatio(Double.MIN_VALUE);

    private final Double value;

    public static QuickRatio from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }
        return new QuickRatio(value);
    }

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }
}
