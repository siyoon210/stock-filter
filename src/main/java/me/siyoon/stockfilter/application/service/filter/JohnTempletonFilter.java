package me.siyoon.stockfilter.application.service.filter;

import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand.JohnTempletonCommand;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.NetIncome;
import me.siyoon.stockfilter.domain.performance.OperatingIncome;
import org.springframework.stereotype.Component;

@Component
class JohnTempletonFilter implements StockFilterI {

    // 템플턴 공식 https://myrichlife.tistory.com/363
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        JohnTempletonCommand command = filterCommand.johnTempletonCommand;

        if (!command.test) {
            return true;
        }

        if (unknownValuePass(command, stockInfo)) {
            return true;
        }

        try {
            double johnTempletonIndex = johnTempletonIndex(stockInfo);
            return johnTempletonIndex < command.threshold;
        } catch (Exception e) {
            return false;
        }
    }

    private double johnTempletonIndex(StockInfo stockInfo) {
        NetIncome threeYearsAgoNetIncome = stockInfo.performanceOf(
                Period.THREE_YEARS_AGO).netIncome;
        NetIncome lastYearsNetIncome = stockInfo.performanceOf(
                Period.LAST_YEAR).netIncome;
        Double growthRate = lastYearsNetIncome.averageGrowthRateFrom(
                threeYearsAgoNetIncome, 2);
        if (growthRate < 0) {
            return Double.MAX_VALUE;
        }
        Double sum = nextFiveTermExpectedNetIncomeSum(
                stockInfo.performanceOf(Period.LAST_YEAR).netIncome, growthRate);
        return (stockInfo.price() * 5) / (sum * 100000000 / stockInfo.tradingInfo.numberOfShare);
    }

    private Double nextFiveTermExpectedNetIncomeSum(NetIncome base, Double growthRate) {
        NetIncome increase1 = base.increase(growthRate);
        NetIncome increase2 = increase1.increase(growthRate);
        NetIncome increase3 = increase2.increase(growthRate);
        NetIncome increase4 = increase3.increase(growthRate);
        return NetIncome.totalValue(base, increase1, increase2, increase3, increase4);
    }

    private boolean unknownValuePass(JohnTempletonCommand command, StockInfo stockInfo) {
        return command.unknownValuePass &&
                (stockInfo.performanceOf(Period.THREE_YEARS_AGO).operatingIncome
                        == OperatingIncome.UNKNOWN_VALUE
                        || stockInfo.performanceOf(Period.LAST_YEAR).operatingIncome
                        == OperatingIncome.UNKNOWN_VALUE
                        || stockInfo.performanceOf(Period.LAST_YEAR).netIncome
                        == NetIncome.UNKNOWN_VALUE);
    }
}
