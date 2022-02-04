package me.siyoon.stockfilter.application.port.in;

import java.util.List;
import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.dto.response.StockInfoResponse;

public interface StockExtractUseCase {

    List<StockInfoResponse> extractedStocks(StockFilterCommand filterCommand);
}
