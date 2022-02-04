package me.siyoon.stockfilter.application.port.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

@ToString
public class Rank {
    public final String type;
    @JsonIgnore
    public final StockCode code;
    public final int value;

    public Rank(String type, StockCode code, int value) {
        this.type = type;
        this.code = code;
        this.value = value;
    }

    public static Rank of(String type, StockCode code, int value) {
        return new Rank(type, code, value);
    }

    public static Rank unknownValue(String type, StockCode code) {
        return new Rank(type, code, Integer.MAX_VALUE);
    }
}
