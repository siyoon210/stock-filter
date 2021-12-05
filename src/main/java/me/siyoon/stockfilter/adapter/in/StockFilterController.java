package me.siyoon.stockfilter.adapter.in;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterUseCase;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockFilterController {

    private final StockFilterUseCase stockFilterUseCase;

    @GetMapping("/stock")
    public List<StockInfo> stock(@RequestBody StockFilterCommand stockFilterCommand) {
        return stockFilterUseCase.filteredStocks(stockFilterCommand);
    }
}
