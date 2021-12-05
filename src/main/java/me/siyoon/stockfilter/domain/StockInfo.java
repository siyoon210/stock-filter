package me.siyoon.stockfilter.domain;

import java.util.List;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class StockInfo {

    public final String name;
    public final String code;
    public final TradingInfo tradingInfo;
    public final Performances performances;

    public List<Performance> performancesIn(List<Period> periods) {
        return performances.in(periods);
    }
}
