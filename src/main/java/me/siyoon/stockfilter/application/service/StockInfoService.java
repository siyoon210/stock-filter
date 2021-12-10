package me.siyoon.stockfilter.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterUseCase;
import me.siyoon.stockfilter.application.service.filter.StockFilter;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockInfoService implements StockFilterUseCase {

    private final StockInfoReader stockInfoReader;
    private final StockFilter stockFilter;

    @Override
    public List<StockInfo> filteredStocks(StockFilterCommand filterCommand) {
        List<StockInfo> stockInfos = stockInfoReader.stockInfos();
        return stockFilter.filteredStocks(filterCommand, stockInfos);
    }
}
