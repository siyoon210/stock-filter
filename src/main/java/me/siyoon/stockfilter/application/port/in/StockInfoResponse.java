package me.siyoon.stockfilter.application.port.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;


@AllArgsConstructor
@Builder
@ToString
public class StockInfoResponse implements Comparable<StockInfoResponse>{

    private static final String NAVER_MAIN_PAGE_URL = "https://finance.naver.com/item/main.nhn?code=";

    public final String name;
    @JsonIgnore
    public final String code;
    public final int expectedDividendYieldRank;
    public final int epsIncreaseRateRank;

    @JsonProperty
    public int totalRank() {
        return expectedDividendYieldRank + epsIncreaseRateRank;
    }

    @JsonProperty
    public String url() {
        return NAVER_MAIN_PAGE_URL + code;
    }

    @Override
    public int compareTo(StockInfoResponse o) {
        return Integer.compare(this.totalRank(), o.totalRank());
    }
}
