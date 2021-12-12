package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.QuickRatioCommand;
import me.siyoon.stockfilter.domain.Performance;
import me.siyoon.stockfilter.domain.Performances;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.QuickRatio;
import me.siyoon.stockfilter.domain.StockInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class QuickRatioFilterTest {

    @InjectMocks
    private QuickRatioFilter dut;

    private static final double THRESHOLD = 100.0;

    @ParameterizedTest(name = "당좌비율이 command 임계값(100)보다 높으면 pass")
    @MethodSource
    void passed_test(Double quickRatioValue, boolean expectedPass) {
        // given
        StockFilterCommand filterCommand = stockFilterCommand();
        StockInfo stockInfo = stockInfo(quickRatioValue);

        // when
        boolean actual = dut.passed(filterCommand, stockInfo);

        // then
        assertThat(actual).isEqualTo(expectedPass);
    }

    private StockFilterCommand stockFilterCommand() {
        QuickRatioCommand quickRatioCommand = QuickRatioCommand.builder()
                                                               .test(true)
                                                               .unknownValuePass(false)
                                                               .periods(List.of(Period.LAST_YEAR))
                                                               .threshold(THRESHOLD)
                                                               .build();
        return StockFilterCommand.builder()
                                 .quickRatio(quickRatioCommand)
                                 .build();
    }

    private StockInfo stockInfo(Double quickRatioValue) {
        Performance performance = Performance.builder()
                                             .quickRatio(new QuickRatio(quickRatioValue))
                                             .build();
        Performances performances = new Performances(Map.of(Period.LAST_YEAR, performance));

        return StockInfo.builder()
                        .performances(performances)
                        .build();
    }

    private static Stream<Arguments> passed_test() {
        return Stream.of(
                Arguments.of(THRESHOLD + 0.1, true),
                Arguments.of(THRESHOLD, false),
                Arguments.of(THRESHOLD - 0.1, false)
        );
    }
}