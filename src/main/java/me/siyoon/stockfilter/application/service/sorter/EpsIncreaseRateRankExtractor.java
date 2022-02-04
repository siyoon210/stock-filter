package me.siyoon.stockfilter.application.service.sorter;

import java.util.Comparator;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.DividendYield;
import org.springframework.stereotype.Component;

@Component
public class EpsIncreaseRateRankExtractor extends RankExtractor{

    @Override
    String type() {
        return "EPS 증가율";
    }

    @Override
    Comparator<StockInfo> comparator() {
        return (stockInfo1, stockInfo2) -> {
            double epsIncreaseRate1 = stockInfo1.epsIncreaseRateFrom(Period.THIS_YEAR_EXPECTED,
                                                                     Period.NEXT_YEAR_EXPECTED);
            double epsIncreaseRate2 = stockInfo2.epsIncreaseRateFrom(Period.THIS_YEAR_EXPECTED,
                                                                     Period.NEXT_YEAR_EXPECTED);
            return Double.compare(epsIncreaseRate2, epsIncreaseRate1);
        };
    }
}
