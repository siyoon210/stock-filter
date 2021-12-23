package me.siyoon.stockfilter.domain.financial;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Equity { // 자본 (순자산)

    public static final Equity UNKNOWN_VALUE = new Equity(Double.MIN_VALUE);

    private final Double value; // 억원
}
