package me.siyoon.stockfilter.application.port.in;

import java.util.HashMap;
import java.util.Map;

public class RankByCode {
    private final String type;
    private final Map<StockCode, Rank> value;

    public RankByCode(String type) {
        this.type = type;
        this.value = new HashMap<>();
    }

    public void put(StockCode code, Rank rank) {
        value.put(code, rank);
    }

    public Rank get(String code) {
        StockCode stockCode = StockCode.of(code);
        return value.getOrDefault(stockCode, Rank.unknownValue(type, stockCode));
    }
}
