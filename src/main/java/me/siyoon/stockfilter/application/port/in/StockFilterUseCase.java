package me.siyoon.stockfilter.application.port.in;

import java.util.List;
import me.siyoon.stockfilter.domain.StockInfo;

public interface StockFilterUseCase {

    List<StockInfo> filteredStocks(StockFilterCommand filterCommand);
}
