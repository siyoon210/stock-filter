package me.siyoon.stockfilter.domain.stability;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Builder
@AllArgsConstructor
@ToString
public class StabilityIndex { // 안정성 지표

    public static final StabilityIndex UNKNOWN_VALUE = unknown();

    public final DebtRatio debtRatio; // 부채비율
    public final QuickRatio quickRatio; // 당좌비율
    public final ReserveRatio reserveRatio; // 유보율

    public static StabilityIndex unknown() {
        return StabilityIndex.builder()
                             .debtRatio(DebtRatio.UNKNOWN_VALUE)
                             .quickRatio(QuickRatio.UNKNOWN_VALUE)
                             .reserveRatio(ReserveRatio.UNKNOWN_VALUE)
                             .build();
    }
}
