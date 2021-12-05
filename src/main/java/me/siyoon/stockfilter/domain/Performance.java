package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Builder
@AllArgsConstructor
@ToString
public class Performance { // 실적 분석

    public static final Performance UNKNOWN_VALUE = Performance.builder()
                                                               .netIncome(NetIncome.UNKNOWN_VALUE)
                                                               .dps(DPS.UNKNOWN_VALUE)
                                                               .build();

    public final NetIncome netIncome; // 당기순이익
    public final DPS dps; // 주당 배당금 (Dividend Per Share)
}
