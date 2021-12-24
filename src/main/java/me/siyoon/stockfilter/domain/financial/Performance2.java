package me.siyoon.stockfilter.domain.financial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import me.siyoon.stockfilter.domain.DPS;
import me.siyoon.stockfilter.domain.DebtRatio;
import me.siyoon.stockfilter.domain.DividendYield;
import me.siyoon.stockfilter.domain.NetIncome;
import me.siyoon.stockfilter.domain.OperatingIncome;
import me.siyoon.stockfilter.domain.PER;
import me.siyoon.stockfilter.domain.QuickRatio;
import me.siyoon.stockfilter.domain.ReserveRatio;

@Builder
@AllArgsConstructor
@ToString
public class Performance2 { // 기업 재무실적

    public static final Performance2 UNKNOWN_VALUE = unknownPerformance();

    public final SalesRevenue salesRevenue; // 매출액
    public final OperatingIncome operatingIncome; // 영업이익
    public final NetIncome netIncome; // 당기순이익
    public final DebtRatio debtRatio; // 부채비율
    public final QuickRatio quickRatio; // 당좌비율
    public final ReserveRatio reserveRatio; // 유보율
    public final Equity equity; // 자본 (순자산)
    public final CFO cfo; // 영업활동현금흐름 (Cash Flows from Operating)
    public final PER per; // 과거 PER
    public final DPS dps; // 과거 주당 배당금 (Dividend Per Share)
    public final DividendYield dividendYield; // 과거 시가배당률

    public static Performance2 unknownPerformance() {
        return Performance2.builder()
                           .salesRevenue(SalesRevenue.UNKNOWN_VALUE)
                           .operatingIncome(OperatingIncome.UNKNOWN_VALUE)
                           .netIncome(NetIncome.UNKNOWN_VALUE)
                           .debtRatio(DebtRatio.UNKNOWN_VALUE)
                           .quickRatio(QuickRatio.UNKNOWN_VALUE)
                           .reserveRatio(ReserveRatio.UNKNOWN_VALUE)
                           .equity(Equity.UNKNOWN_VALUE)
                           .cfo(CFO.UNKNOWN_VALUE)
                           .per(PER.UNKNOWN_VALUE)
                           .dps(DPS.UNKNOWN_VALUE)
                           .dividendYield(DividendYield.UNKNOWN_VALUE)
                           .build();
    }
}
