package me.siyoon.stockfilter.application.port.in;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class StockCode {
    public final String value;

    private StockCode(String value) {
        this.value = value;
    }

    public static StockCode of(String code) {
        return new StockCode(code);
    }
}
