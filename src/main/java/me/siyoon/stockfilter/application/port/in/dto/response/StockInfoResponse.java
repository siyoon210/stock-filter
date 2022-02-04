package me.siyoon.stockfilter.application.port.in.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;


@AllArgsConstructor
@Builder
public class StockInfoResponse implements Comparable<StockInfoResponse> {

    private static final String NAVER_MAIN_PAGE_URL = "https://finance.naver.com/item/main.nhn?code=";

    public final String name;
    public final String url;
    public final List<Rank> ranks;
    public final int totalRank;

    public StockInfoResponse(String name, String code, List<Rank> ranks) {
        this.name = name;
        this.url = NAVER_MAIN_PAGE_URL + code;
        this.ranks = ranks;
        this.totalRank = totalRank();
    }

    private int totalRank() {
        return ranks.stream()
                    .mapToInt(rank -> rank.value)
                    .sum();
    }

    @Override
    public int compareTo(StockInfoResponse o) {
        return Integer.compare(this.totalRank(), o.totalRank());
    }
}
