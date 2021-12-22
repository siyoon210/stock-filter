package me.siyoon.stockfilter.domain.financial;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SalesRevenue { // 매출액

    public static final SalesRevenue UNKNOWN_VALUE = new SalesRevenue(Double.MIN_VALUE);

    private final Double value;
}
