package me.siyoon.stockfilter.application.port.in.dto.response;

public class Rank {
    public final String type;
    public final int value;

    public Rank(String type,int value) {
        this.type = type;
        this.value = value;
    }

    public static Rank of(String type, int value) {
        return new Rank(type, value);
    }

    public static Rank unknownValue(String type) {
        return new Rank(type, Integer.MAX_VALUE);
    }
}
