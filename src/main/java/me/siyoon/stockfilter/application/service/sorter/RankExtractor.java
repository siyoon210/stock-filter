package me.siyoon.stockfilter.application.service.sorter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import me.siyoon.stockfilter.application.port.in.dto.response.Rank;
import me.siyoon.stockfilter.application.port.in.dto.response.RankByCode;
import me.siyoon.stockfilter.domain.StockInfo;

public abstract class RankExtractor implements StockRankExtractorI {

    @Override
    public RankByCode rankByCode(List<StockInfo> stockInfos) {
        RankByCode rankByCode = new RankByCode(type());
        List<StockInfo> sortedStockInfos = stockInfos.stream()
                                                     .sorted(comparator())
                                                     .collect(Collectors.toList());
        for (int i = 0; i < sortedStockInfos.size(); i++) {
            Rank rank = Rank.of(type(), i + 1);
            rankByCode.put(sortedStockInfos.get(i).code, rank);
        }
        return rankByCode;
    }

    abstract String type();

    abstract Comparator<StockInfo> comparator();
}
