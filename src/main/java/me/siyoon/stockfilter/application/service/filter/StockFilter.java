package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockFilter {

    private final List<StockFilterI> stockInfoFilters;

    public List<StockInfo> filteredStocks(StockFilterCommand filterCommand,
                                          List<StockInfo> allStockInfos) {
        return allStockInfos.stream()
                            .filter(stockInfo -> passed(filterCommand, stockInfo))
                            .collect(Collectors.toList());
    }

    private boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        return stockInfoFilters.stream()
                               .allMatch(filter -> filter.passed(filterCommand, stockInfo));
    }
}
