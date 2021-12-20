package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GrossProfitMargin { // 매출총이익률

    public static final GrossProfitMargin UNKNOWN_VALUE = new GrossProfitMargin(Double.MIN_VALUE);

    private final Double value;
}
