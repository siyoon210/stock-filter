package me.siyoon.stockfilter.application.port.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.Builder;
import lombok.ToString;
import me.siyoon.stockfilter.domain.Period;

@ToString
@Builder
public class StockFilterCommand {

    public final OperatingIncomeCommand operatingIncome; // 영업이익
    public final NetIncomeCommand netIncome;  // 당기순이익
    public final DebtRatioCommand debtRatio; // 부채비율
    public final QuickRatioCommand quickRatio; // 당좌비율
    public final ReserveRatioCommand reserveRatio; // 유보율
    public final PerCommand per; // PER
    public final ExpectedPerCommand expectedPer; // 예상(추정) PER
    public final ImprovedPerCommand improvedPer; // per 개선
    public final DividendYieldCommand dividendYield; // 시가배당률
    public final ExpectedDividendYieldCommand expectedDividendYield; // 예상 배당률
    public final AnnualPriceVolatilityCommand annualPriceVolatility; // 52주 최고가 / 52주 최저가
    public final AnnualHigherCurrentPriceRatioCommand annualHigherCurrentPriceRatio; // 52주 최고가 / 현재가

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public StockFilterCommand(
            OperatingIncomeCommand operatingIncome,
            NetIncomeCommand netIncome,
            DebtRatioCommand debtRatio,
            QuickRatioCommand quickRatio,
            ReserveRatioCommand reserveRatio,
            PerCommand per,
            ExpectedPerCommand expectedPer,
            ImprovedPerCommand improvedPer,
            DividendYieldCommand dividendYield,
            ExpectedDividendYieldCommand expectedDividendYield,
            AnnualPriceVolatilityCommand annualPriceVolatility,
            AnnualHigherCurrentPriceRatioCommand annualHigherCurrentPriceRatio) {
        this.operatingIncome = operatingIncome;
        this.netIncome = netIncome;
        this.debtRatio = debtRatio;
        this.quickRatio = quickRatio;
        this.reserveRatio = reserveRatio;
        this.per = per;
        this.expectedPer = expectedPer;
        this.improvedPer = improvedPer;
        this.dividendYield = dividendYield;
        this.expectedDividendYield = expectedDividendYield;
        this.annualPriceVolatility = annualPriceVolatility;
        this.annualHigherCurrentPriceRatio = annualHigherCurrentPriceRatio;
    }

    @ToString
    @Builder
    public static class OperatingIncomeCommand { // 영업이익

        public final boolean test;
        public final boolean unknownValuePass;
        public final List<Period> periods;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public OperatingIncomeCommand(boolean test, boolean unknownValuePass, List<Period> periods,
                                      Double threshold) {
            this.test = test;
            this.unknownValuePass = unknownValuePass;
            this.periods = periods;
            this.threshold = threshold;
        }
    }

    @ToString
    public static class NetIncomeCommand { // 당기순이익

        public final boolean test;
        public final boolean unknownValuePass;
        public final List<Period> periods;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public NetIncomeCommand(boolean test, boolean unknownValuePass, List<Period> periods,
                                Double threshold) {
            this.test = test;
            this.unknownValuePass = unknownValuePass;
            this.periods = periods;
            this.threshold = threshold;
        }
    }


    @ToString
    @Builder
    public static class DebtRatioCommand { // 부채비율

        public final boolean test;
        public final boolean unknownValuePass;
        public final List<Period> periods;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public DebtRatioCommand(boolean test, boolean unknownValuePass,
                                List<Period> periods,
                                Double threshold) {
            this.test = test;
            this.unknownValuePass = unknownValuePass;
            this.periods = periods;
            this.threshold = threshold;
        }
    }

    @ToString
    @Builder
    public static class QuickRatioCommand { // 당좌비율

        public final boolean test;
        public final boolean unknownValuePass;
        public final List<Period> periods;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public QuickRatioCommand(boolean test, boolean unknownValuePass,
                                 List<Period> periods,
                                 Double threshold) {
            this.test = test;
            this.unknownValuePass = unknownValuePass;
            this.periods = periods;
            this.threshold = threshold;
        }
    }

    @ToString
    @Builder
    public static class ReserveRatioCommand { // 유보율

        public final boolean test;
        public final boolean unknownValuePass;
        public final List<Period> periods;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public ReserveRatioCommand(boolean test, boolean unknownValuePass,
                                 List<Period> periods,
                                 Double threshold) {
            this.test = test;
            this.unknownValuePass = unknownValuePass;
            this.periods = periods;
            this.threshold = threshold;
        }
    }

    @ToString
    @Builder
    public static class PerCommand { // PER

        public final boolean test;
        public final boolean unknownValuePass;
        public final List<Period> periods;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public PerCommand(boolean test, boolean unknownValuePass,
                          List<Period> periods,
                          Double threshold) {
            this.test = test;
            this.unknownValuePass = unknownValuePass;
            this.periods = periods;
            this.threshold = threshold;
        }
    }

    @ToString
    @Builder
    public static class ExpectedPerCommand { // 예상(추정) PER

        public final boolean test;
        public final boolean unknownValuePass;
        public final List<Period> periods = List.of(Period.THIS_YEAR_EXPECTED);
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public ExpectedPerCommand(boolean test, boolean unknownValuePass,
                                  Double threshold) {
            this.test = test;
            this.unknownValuePass = unknownValuePass;
            this.threshold = threshold;
        }
    }

    @Builder
    @ToString
    public static class ImprovedPerCommand { // basePeriod 기준 targetPeriod 개선된 PER

        public final boolean test;
        public final boolean unknownValuePass;
        public final Period basePeriod;
        public final Period targetPeriod;
        public final Double ratio;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public ImprovedPerCommand(boolean test, boolean unknownValuePass, Period basePeriod,
                                  Period targetPeriod, Double ratio) {
            this.test = test;
            this.unknownValuePass = unknownValuePass;
            this.basePeriod = basePeriod;
            this.targetPeriod = targetPeriod;
            this.ratio = ratio;
        }
    }

    @ToString
    @Builder
    public static class DividendYieldCommand { // 시가배당률

        public final boolean test;
        public final boolean unknownValuePass;
        public final List<Period> periods;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public DividendYieldCommand(boolean test, boolean unknownValuePass,
                                    List<Period> periods,
                                    Double threshold) {
            this.test = test;
            this.unknownValuePass = unknownValuePass;
            this.periods = periods;
            this.threshold = threshold;
        }
    }

    @ToString
    public static class ExpectedDividendYieldCommand { // 예상 배당률

        public final boolean test;
        public final boolean unknownValuePass;
        public final List<Period> periods = List.of(Period.THIS_YEAR_EXPECTED);
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public ExpectedDividendYieldCommand(boolean test, boolean unknownValuePass,
                                            Double threshold) {
            this.test = test;
            this.unknownValuePass = unknownValuePass;
            this.threshold = threshold;
        }
    }

    @ToString
    public static class AnnualPriceVolatilityCommand { // 52주 최고가 / 52주 최저가

        public final boolean test;
        public final boolean unknownValuePass;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public AnnualPriceVolatilityCommand(boolean test, boolean unknownValuePass,
                                            Double threshold) {
            this.test = test;
            this.unknownValuePass = unknownValuePass;
            this.threshold = threshold;
        }
    }

    @ToString
    public static class AnnualHigherCurrentPriceRatioCommand { // 52주 최고가 / 현재주가

        public final boolean test;
        public final boolean unknownValuePass;
        public final Double minThreshold;
        public final Double maxThreshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public AnnualHigherCurrentPriceRatioCommand(boolean test, boolean unknownValuePass,
                                            Double minThreshold, Double maxThreshold) {
            this.test = test;
            this.unknownValuePass = unknownValuePass;
            this.minThreshold = minThreshold;
            this.maxThreshold = maxThreshold;
        }
    }
}
