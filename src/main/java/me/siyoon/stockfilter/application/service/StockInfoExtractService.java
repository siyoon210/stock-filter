package me.siyoon.stockfilter.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockExtractUseCase;
import me.siyoon.stockfilter.application.service.filter.StockFilter;
import me.siyoon.stockfilter.application.service.sorter.StockSorter;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockInfoExtractService implements StockExtractUseCase {

    private final StockInfoReader stockInfoReader;
    private final StockFilter stockFilter;
    private final StockSorter stockSorter;

    @Override
    public List<StockInfo> extractedStocks(StockFilterCommand filterCommand) {
        List<StockInfo> stockInfos = stockInfoReader.stockInfos();
        List<StockInfo> filteredStocks = stockFilter.filteredStocks(filterCommand, stockInfos);
        return stockSorter.sortedStocks(filteredStocks);
    }
}
