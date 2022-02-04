package me.siyoon.stockfilter.application.port.in;

import java.util.List;
import me.siyoon.stockfilter.domain.StockInfo;

public interface StockExtractUseCase {

    List<StockInfoResponse> extractedStocks(StockFilterCommand filterCommand);
}
