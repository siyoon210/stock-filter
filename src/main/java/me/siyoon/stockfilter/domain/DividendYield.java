package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DividendYield { // 배당률
    private final Double value;

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }
}
