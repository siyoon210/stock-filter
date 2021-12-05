package me.siyoon.stockfilter.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;


@AllArgsConstructor
@Builder
public class StockInfo {

    public final String name;
    public final String code;
    public final TradingInfo tradingInfo; // 거래 정보
    public final Performances performances; // 실적

    public List<Performance> performancesIn(List<Period> periods) {
        return performances.in(periods);
    }
}
