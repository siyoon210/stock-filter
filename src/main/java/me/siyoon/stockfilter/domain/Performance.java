package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Performance { // 실적 분석

    public final NetIncome netIncome; // 당기순이익
}
