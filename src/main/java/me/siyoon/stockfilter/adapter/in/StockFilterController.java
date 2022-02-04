package me.siyoon.stockfilter.adapter.in;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.siyoon.stockfilter.application.port.in.StockExtractUseCase;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockInfoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockFilterController {

    private final StockExtractUseCase stockExtractUseCase;

    @GetMapping("/stock")
    public List<StockInfoResponse> stock(@RequestBody StockFilterCommand stockFilterCommand) {
        return stockExtractUseCase.extractedStocks(stockFilterCommand);
    }
}
