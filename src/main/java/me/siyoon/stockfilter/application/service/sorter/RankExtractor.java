package me.siyoon.stockfilter.application.service.sorter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import me.siyoon.stockfilter.application.port.in.Rank;
import me.siyoon.stockfilter.application.port.in.RankByCode;
import me.siyoon.stockfilter.application.port.in.StockCode;
import me.siyoon.stockfilter.domain.StockInfo;

public abstract class RankExtractor implements StockRankExtractorI {

    @Override
    public RankByCode rankByCode(List<StockInfo> stockInfos) {
        RankByCode rankByCode = new RankByCode(type());
        List<StockInfo> sortedStockInfos = stockInfos.stream()
                                                     .sorted(comparator())
                                                     .collect(Collectors.toList());
        for (int i = 0; i < sortedStockInfos.size(); i++) {
            StockCode stockCode = StockCode.of(sortedStockInfos.get(i).code);
            Rank rank = Rank.of(type(), stockCode, i + 1);
            rankByCode.put(stockCode, rank);
        }
        return rankByCode;
    }

    abstract String type();

    abstract Comparator<StockInfo> comparator();
}
