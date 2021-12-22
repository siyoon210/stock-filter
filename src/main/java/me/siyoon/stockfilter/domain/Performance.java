package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import me.siyoon.stockfilter.domain.financial.SalesRevenue;

@Builder
@AllArgsConstructor
@ToString
public class Performance { // 기업 실적

    public static final Performance UNKNOWN_VALUE = unknownPerformance();

    public final SalesRevenue salesRevenue; // 매출액
    public final GrossProfitMargin grossProfitMargin; // 매출총이익률
    public final OperatingIncome operatingIncome; // 영업이익
    public final NetIncome netIncome; // 당기순이익
    public final DebtRatio debtRatio; // 부채비율
    public final QuickRatio quickRatio; // 당좌비율
    public final ReserveRatio reserveRatio; // 유보율
    public final Asset asset; // 자산
    public final PER per; // todo 투자 지표로 이동
    public final DPS dps; // 주당 배당금 (Dividend Per Share)
    public final DividendYield dividendYield; // 시가배당률

    public static Performance unknownPerformance() {
        return Performance.builder()
                          .operatingIncome(OperatingIncome.UNKNOWN_VALUE)
                          .netIncome(NetIncome.UNKNOWN_VALUE)
                          .debtRatio(DebtRatio.UNKNOWN_VALUE)
                          .quickRatio(QuickRatio.UNKNOWN_VALUE)
                          .reserveRatio(ReserveRatio.UNKNOWN_VALUE)
                          .per(PER.UNKNOWN_VALUE)
                          .dps(DPS.UNKNOWN_VALUE)
                          .dividendYield(DividendYield.UNKNOWN_VALUE)
                          .build();
    }
}
