package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NetIncome { // 당기순이익

    private final Double value;

    public boolean isGraterThan(Double value) {
        return this.value > value;
    }
}
