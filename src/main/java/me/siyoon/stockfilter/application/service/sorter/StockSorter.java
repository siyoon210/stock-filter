package me.siyoon.stockfilter.application.service.sorter;

import java.util.List;
import java.util.stream.Collectors;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.DividendYield;
import org.springframework.stereotype.Component;

@Component
public class StockSorter {

    public List<StockInfo> sortedStocks(List<StockInfo> stockInfos) {
        return stockInfos.stream()
                         .sorted((stockInfo1, stockInfo2) -> {
                             DividendYield dividendYield1 = stockInfo1.expectedDividendYieldOf(
                                     Period.NEXT_YEAR_EXPECTED);
                             DividendYield dividendYield2 = stockInfo2.expectedDividendYieldOf(
                                     Period.NEXT_YEAR_EXPECTED);
                             return dividendYield1.compareTo(dividendYield2);
                         })
                         .collect(Collectors.toList());
    }
}
