package me.siyoon.stockfilter.domain.performance;

import java.io.Serializable;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class NetIncome implements Serializable { // 당기순이익

    public static final NetIncome UNKNOWN_VALUE = new NetIncome(Double.MIN_VALUE);

    private final Double value;

    public static NetIncome from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }
        return new NetIncome(value);
    }

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }

    public static Double totalValue(NetIncome... netIncomes) {
        return Arrays.stream(netIncomes)
                     .map(netIncome -> netIncome.value)
                     .reduce(0.0, Double::sum);
    }
}
