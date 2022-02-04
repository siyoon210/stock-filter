package me.siyoon.stockfilter.application.service.sorter;

import java.util.Comparator;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.DividendYield;
import org.springframework.stereotype.Component;

@Component
public class ExpectedDividendYieldRankExtractor extends RankExtractor{

    @Override
    String type() {
        return "예상 시가 배당률";
    }

    @Override
    Comparator<StockInfo> comparator() {
        return (stockInfo1, stockInfo2) -> {
            DividendYield dividendYield1
                    = stockInfo1.expectedDividendYieldOf(Period.NEXT_YEAR_EXPECTED);
            DividendYield dividendYield2
                    = stockInfo2.expectedDividendYieldOf(Period.NEXT_YEAR_EXPECTED);
            return dividendYield1.compareTo(dividendYield2);
        };
    }
}
