package me.siyoon.stockfilter.application.port.in;

import lombok.Builder;
import lombok.ToString;
import me.siyoon.stockfilter.domain.StockInfo;

@ToString
public class StockFilterResponse implements Comparable<StockFilterResponse>{
    private static final String NAVER_URL = "https://finance.naver.com/item/main.nhn?code=";

    public final StockInfo stockInfo;
    public final Integer totalRank;
    public final Integer perRank;
    public final Integer pbrRank;
    public final Integer pcrRank;
    public final Integer psrRank;
    public final String naverLink;

    @Builder
    public StockFilterResponse(StockInfo stockInfo, Integer totalRank, Integer perRank,
                               Integer pbrRank, Integer pcrRank, Integer psrRank) {
        this.stockInfo = stockInfo;
        this.totalRank = totalRank;
        this.perRank = perRank;
        this.pbrRank = pbrRank;
        this.pcrRank = pcrRank;
        this.psrRank = psrRank;
        this.naverLink = NAVER_URL + stockInfo.code;
    }

    @Override
    public int compareTo(StockFilterResponse o) {
        return totalRank.compareTo(o.totalRank);
    }
}
