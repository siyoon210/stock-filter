package me.siyoon.stockfilter.application.service.filter;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand.JohnTempletonCommand;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.EPS;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class JohnTempletonFilter implements StockFilterI {

    // 템플턴 공식 https://myrichlife.tistory.com/363
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        JohnTempletonCommand command = filterCommand.johnTempletonCommand;

        if (!command.test) {
            return true;
        }

        if (hasUnknownValuePass(stockInfo)) {
            return false;
        }

        try {
            double johnTempletonIndex = johnTempletonIndex(stockInfo);
            return johnTempletonIndex < command.threshold && johnTempletonIndex > 0 && johnTempletonIndex != Double.MIN_VALUE;
        } catch (Exception e) {
            log.warn("JohnTempleton Exception {} {}", stockInfo.name, stockInfo.code);
            return false;
        }
    }

    private double johnTempletonIndex(StockInfo stockInfo) {
        Double growthRate = growthRate(stockInfo);
        if (growthRate.isNaN() || growthRate.isInfinite() || growthRate <= 0) {
            log.info("Could not calculate growthRate. {}, rate:{}", stockInfo.name, growthRate);
            return Double.MIN_VALUE;
        }

        return stockInfo.price() * 5 /
                nextFiveTermExpectedEpsSum(stockInfo.performanceOf(Period.LAST_YEAR).eps,
                                           growthRate);
    }

    private Double growthRate(StockInfo stockInfo) {
        EPS fiveYearsAgoEps = stockInfo.performanceOf(Period.FIVE_YEARS_AGO).eps;
        EPS fourYearsAgoEps = stockInfo.performanceOf(Period.FOUR_YEARS_AGO).eps;
        EPS threeYearsAgoEps = stockInfo.performanceOf(Period.THREE_YEARS_AGO).eps;
        EPS twoYearsAgoEps = stockInfo.performanceOf(Period.TWO_YEARS_AGO).eps;
        EPS lastYearEps = stockInfo.performanceOf(Period.LAST_YEAR).eps;

        Double rate1 = fiveYearsAgoEps.increaseRate(fourYearsAgoEps);
        Double rate2 = fourYearsAgoEps.increaseRate(threeYearsAgoEps);
        Double rate3 = threeYearsAgoEps.increaseRate(twoYearsAgoEps);
        Double rate4 = twoYearsAgoEps.increaseRate(lastYearEps);

        Double max = max(rate1, rate2, rate3, rate4);
        return (rate1 + rate2 + rate3 + rate4 - max) / 3;
    }

    private Double max(Double... doubles) {
        return Arrays.stream(doubles)
                     .mapToDouble(d -> d)
                     .max().orElse(Double.MAX_VALUE);
    }

    private Double nextFiveTermExpectedEpsSum(EPS base, Double growthRate) {
        EPS increase1 = base.increase(growthRate);
        EPS increase2 = increase1.increase(growthRate);
        EPS increase3 = increase2.increase(growthRate);
        EPS increase4 = increase3.increase(growthRate);
        return EPS.totalValue(base, increase1, increase2, increase3, increase4);
    }

    private boolean hasUnknownValuePass(StockInfo stockInfo) {
        return stockInfo.performanceOf(Period.FIVE_YEARS_AGO).eps
                        == EPS.UNKNOWN_VALUE
                        || stockInfo.performanceOf(Period.FOUR_YEARS_AGO).eps
                        == EPS.UNKNOWN_VALUE
                        || stockInfo.performanceOf(Period.THREE_YEARS_AGO).eps
                        == EPS.UNKNOWN_VALUE
                        || stockInfo.performanceOf(Period.TWO_YEARS_AGO).eps
                        == EPS.UNKNOWN_VALUE
                        || stockInfo.performanceOf(Period.LAST_YEAR).eps
                        == EPS.UNKNOWN_VALUE;
    }
}
