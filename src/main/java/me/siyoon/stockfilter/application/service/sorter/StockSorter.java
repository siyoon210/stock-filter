package me.siyoon.stockfilter.application.service.sorter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import me.siyoon.stockfilter.application.port.in.StockInfoResponse;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.DividendYield;
import org.springframework.stereotype.Component;

@Component
public class StockSorter {

    public List<StockInfoResponse> sortedStocks(List<StockInfo> stockInfos) {
        Map<String, Integer> expectedDividendYieldRankByCode
                = expectedDividendYieldRankByCode(stockInfos);
        Map<String, Integer> epsIncreaseRateRankByCode = epsIncreaseRateRankByCode(stockInfos);
        return stockInfos.stream()
                         .map(stockInfo -> stockInfoResponse(expectedDividendYieldRankByCode,
                                                         epsIncreaseRateRankByCode, stockInfo))
                         .sorted()
                         .collect(Collectors.toList());
    }

    private StockInfoResponse stockInfoResponse(
            Map<String, Integer> expectedDividendYieldRankByCode,
            Map<String, Integer> epsIncreaseRateRankByCode, StockInfo stockInfo) {
        String code = stockInfo.code;
        return new StockInfoResponse(stockInfo.name, code,
                                     expectedDividendYieldRankByCode.getOrDefault(
                                             code, Integer.MAX_VALUE),
                                     epsIncreaseRateRankByCode.getOrDefault(
                                             code, Integer.MAX_VALUE));
    }

    private Map<String, Integer> expectedDividendYieldRankByCode(List<StockInfo> stockInfos) {
        Map<String, Integer> expectedDividendYieldRankByCode = new HashMap<>();
        List<StockInfo> sortedStockInfos = stockInfos.stream()
                                                     .sorted(expectedDividendYieldComparator())
                                                     .collect(Collectors.toList());
        for (int i = 0; i < sortedStockInfos.size(); i++) {
            expectedDividendYieldRankByCode.put(sortedStockInfos.get(i).code, i + 1);
        }
        return expectedDividendYieldRankByCode;
    }

    private Comparator<StockInfo> expectedDividendYieldComparator() {
        return (stockInfo1, stockInfo2) -> {
            DividendYield dividendYield1 = stockInfo1.expectedDividendYieldOf(
                    Period.NEXT_YEAR_EXPECTED);
            DividendYield dividendYield2 = stockInfo2.expectedDividendYieldOf(
                    Period.NEXT_YEAR_EXPECTED);
            return dividendYield1.compareTo(dividendYield2);
        };
    }

    private Map<String, Integer> epsIncreaseRateRankByCode(List<StockInfo> stockInfos) {
        Map<String, Integer> epsIncreaseRateRankByCode = new HashMap<>();
        List<StockInfo> sortedStockInfos = stockInfos.stream()
                                                     .sorted(epsIncreaseRateComparator())
                                                     .collect(Collectors.toList());
        for (int i = 0; i < sortedStockInfos.size(); i++) {
            epsIncreaseRateRankByCode.put(sortedStockInfos.get(i).code, i + 1);
        }
        return epsIncreaseRateRankByCode;
    }

    private Comparator<StockInfo> epsIncreaseRateComparator() {
        return (stockInfo1, stockInfo2) -> {
            double epsIncreaseRate1 = stockInfo1.epsIncreaseRateFrom(Period.THIS_YEAR_EXPECTED,
                                                                     Period.NEXT_YEAR_EXPECTED);
            double epsIncreaseRate2 = stockInfo2.epsIncreaseRateFrom(Period.THIS_YEAR_EXPECTED,
                                                                     Period.NEXT_YEAR_EXPECTED);
            return Double.compare(epsIncreaseRate2, epsIncreaseRate1);
        };
    }
}
