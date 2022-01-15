package me.siyoon.stockfilter.adapter.in;

import java.util.List;
import java.util.stream.Collectors;
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
    public List<String> stock(@RequestBody StockFilterCommand stockFilterCommand) {
        List<StockInfo> stockInfos = stockFilterUseCase.filteredStocks(stockFilterCommand);
        String naverMainPageUrl = "https://finance.naver.com/item/main.nhn?code=";
        return stockInfos.stream()
                         .map(stockInfo -> stockInfo.name + ": " + naverMainPageUrl + stockInfo.code)
                         .collect(Collectors.toList());
    }
}
