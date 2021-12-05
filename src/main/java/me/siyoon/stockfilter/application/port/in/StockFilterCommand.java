package me.siyoon.stockfilter.application.port.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.ToString;
import me.siyoon.stockfilter.domain.Period;

@ToString
public class StockFilterCommand {
    public final NetIncomeCommand netIncome;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public StockFilterCommand(NetIncomeCommand netIncome) {
        this.netIncome = netIncome;
    }

    @ToString
    public static class NetIncomeCommand {

        public final List<Period> periods;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public NetIncomeCommand(List<Period> periods, Double threshold) {
            this.periods = periods;
            this.threshold = threshold;
        }
    }
}
