package me.siyoon.stockfilter.domain.performance;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class OperatingIncome implements Serializable { // 영업이익

    public static final OperatingIncome UNKNOWN_VALUE = new OperatingIncome(Double.MIN_VALUE);

    private final Double value;

    public static OperatingIncome from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }

        return new OperatingIncome(value);
    }

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }
}
