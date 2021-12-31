package me.siyoon.stockfilter.application.service.filter;

import java.util.Map;
import java.util.stream.Stream;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.ImprovedPerCommand;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.TradingInfo;
import me.siyoon.stockfilter.domain.performance.EPS;
import me.siyoon.stockfilter.domain.performance.Performance;
import me.siyoon.stockfilter.domain.performance.Performances;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ImprovedPerFilterTest {

    @InjectMocks
    private ImprovedPerFilter dut;

    private static final double RATIO = 20;

    @ParameterizedTest(name = "(예상 PER)이 작년대비 RATIO(20) 이상 개선되었으면 pass")
    @MethodSource
    void passed_test(Double basePeriodEPS, Double targetPeriodEPS, boolean expect) {
        // given
        StockFilterCommand filterCommand = stockFilterCommand();
        StockInfo stockInfo = stockInfo(basePeriodEPS, targetPeriodEPS);

        // when
        boolean actual = dut.passed(filterCommand, stockInfo);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> passed_test() {
        return Stream.of(
//                Arguments.of(55.0, 72.0, true),
//                Arguments.of(100.0, 126.5, true),
//                Arguments.of(-5.0, 30.0, true),
//                Arguments.of(-9.0, 1632.0, true),
//                Arguments.of(5.0, -30.0, false),
//                Arguments.of(20.0, 2.5, false),
//                Arguments.of(100.0, 125.0, false),
                Arguments.of(-2820.0, null, false)
        );
    }

    private StockFilterCommand stockFilterCommand() {
        ImprovedPerCommand improvedPerCommand = ImprovedPerCommand.builder()
                                                                  .test(true)
                                                                  .ratio(RATIO)
                                                                  .basePeriod(Period.LAST_YEAR)
                                                                  .targetPeriod(Period.THIS_YEAR_EXPECTED)
                                                                  .build();
        return StockFilterCommand.builder()
                                 .improvedPer(improvedPerCommand)
                                 .build();
    }

    private StockInfo stockInfo(Double basePeriodEPS, Double targetPeriodEPS) {
        Performance basePeriodPerformance = Performance.builder()
                                                     .eps(EPS.from(basePeriodEPS))
                                                     .build();

        Performance targetPeriodPerformance = Performance.builder()
                                                     .eps(EPS.from(targetPeriodEPS))
                                                     .build();

        Performances performances = new Performances(Map.of(Period.LAST_YEAR, basePeriodPerformance,
                                                            Period.THIS_YEAR_EXPECTED, targetPeriodPerformance));

        return StockInfo.builder()
                        .tradingInfo(TradingInfo.builder()
                                                .price(1000.0)
                                                .build())
                        .performances(performances)
                        .build();
    }
}