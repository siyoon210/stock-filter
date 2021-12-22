package me.siyoon.stockfilter.domain.financial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import me.siyoon.stockfilter.domain.NetIncome;

@Builder
@AllArgsConstructor
@ToString
public class Performance2 { // 기업 재무실적

    public static final Performance2 UNKNOWN_VALUE = unknownPerformance();

    public final SalesRevenue salesRevenue; // 매출액
    public final NetIncome netIncome; // 당기 순이익

    public static Performance2 unknownPerformance() {
        return Performance2.builder()
                           .salesRevenue(SalesRevenue.UNKNOWN_VALUE)
                           .netIncome(NetIncome.UNKNOWN_VALUE)
                           .build();
    }
}
