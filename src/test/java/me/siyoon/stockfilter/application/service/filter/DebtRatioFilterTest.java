package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.DebtRatioCommand;
import me.siyoon.stockfilter.domain.DebtRatio;
import me.siyoon.stockfilter.domain.Performance;
import me.siyoon.stockfilter.domain.Performances;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.StockInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DebtRatioFilterTest {

    @InjectMocks
    private DebtRatioFilter dut;

    @ParameterizedTest(name = "부채비율이 임계값(200)보다 낮으면 pass")
    @MethodSource
    void passed_test(Double debtRatioValue, boolean expectedPass) {
        // given
        StockFilterCommand filterCommand = stockFilterCommand();
        StockInfo stockInfo = stockInfo(debtRatioValue);

        // when
        boolean actual = dut.passed(filterCommand, stockInfo);

        // then
        assertThat(actual).isEqualTo(expectedPass);
    }

    private StockInfo stockInfo(Double debtRatioValue) {
        Performance performance = Performance.builder()
                                       .debtRatio(new DebtRatio(debtRatioValue))
                                       .build();
        Performances performances = new Performances(Map.of(Period.LAST_YEAR, performance));

        return StockInfo.builder()
                        .performances(performances)
                        .build();
    }

    private StockFilterCommand stockFilterCommand() {
        DebtRatioCommand debtRatioCommand = DebtRatioCommand.builder()
                                                 .skip(false)
                                                 .unknownValuePass(false)
                                                 .periods(List.of(Period.LAST_YEAR))
                                                 .threshold(200.0)
                                                 .build();
        return StockFilterCommand.builder()
                                 .debtRatio(debtRatioCommand)
                                 .build();
    }

    private static Stream<Arguments> passed_test() {
        return Stream.of(
                Arguments.of(200.1, false),
                Arguments.of(200.0, false),
                Arguments.of(199.9, true)
        );
    }
}