package me.siyoon.stockfilter.application.service.sorter;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.siyoon.stockfilter.application.port.in.dto.response.Rank;
import me.siyoon.stockfilter.application.port.in.dto.response.RankByCode;
import me.siyoon.stockfilter.application.port.in.dto.response.StockInfoResponse;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockSorter {

    private final List<StockRankExtractorI> stockRankExtractors;

    public List<StockInfoResponse> sortedStocks(List<StockInfo> stockInfos) {
        List<RankByCode> rankByCodes = rankByCodes(stockInfos);

        return stockInfos.stream()
                         .map(stockInfo -> stockInfoResponse(rankByCodes, stockInfo))
                         .sorted()
                         .collect(Collectors.toList());
    }

    private List<RankByCode> rankByCodes(List<StockInfo> stockInfos) {
        return stockRankExtractors.stream()
                                  .map(rankExtractor -> rankExtractor.rankByCode(stockInfos))
                                  .collect(Collectors.toList());
    }

    private StockInfoResponse stockInfoResponse(List<RankByCode> rankByCodes, StockInfo stockInfo) {
        return new StockInfoResponse(stockInfo.name, stockInfo.code, ranks(rankByCodes, stockInfo));
    }

    private List<Rank> ranks(List<RankByCode> rankByCodes, StockInfo stockInfo) {
        return rankByCodes.stream()
                          .map(rankByCode -> rankByCode.get(stockInfo.code))
                          .collect(Collectors.toList());
    }
}
