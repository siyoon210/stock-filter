package me.siyoon.stockfilter.domain;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class NetIncome { // 당기순이익

    public static final NetIncome UNKNOWN_VALUE = new NetIncome(Double.MIN_VALUE);

    private final Double value;

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }

    public static Double totalValue(NetIncome... netIncomes) {
        return Arrays.stream(netIncomes)
                     .map(netIncome -> netIncome.value)
                     .reduce(0.0, Double::sum);
    }
}
