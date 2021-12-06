package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Builder
@AllArgsConstructor
@ToString
public class Performance { // 기업 실적

    public static final Performance UNKNOWN_VALUE = Performance.builder()
                                                               .netIncome(NetIncome.UNKNOWN_VALUE)
                                                               .debtRatio(DebtRatio.UNKNOWN_VALUE)
                                                               .dps(DPS.UNKNOWN_VALUE)
                                                               .build();

    public final NetIncome netIncome; // 당기순이익
    public final DebtRatio debtRatio; // 부채 비율
    public final DPS dps; // 주당 배당금 (Dividend Per Share)
}
