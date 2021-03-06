package me.siyoon.stockfilter.application.port.in.dto.response;

import java.util.HashMap;
import java.util.Map;

public class RankByCode {
    private final String type;
    private final Map<String, Rank> value;

    public RankByCode(String type) {
        this.type = type;
        this.value = new HashMap<>();
    }

    public void put(String code, Rank rank) {
        value.put(code, rank);
    }

    public Rank get(String code) {
        return value.getOrDefault(code, Rank.unknownValue(type));
    }
}
