package me.siyoon.stockfilter.adapter.out.stockinfo.fnguide.converter.performance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.NetIncome;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class NetIncomeParserTest {

    private final NetIncomeParser dut = new NetIncomeParser();

    private static Document MAIN_PAGE;

    @BeforeAll
    static void setUp() throws IOException {
        File sampleHtml = new File("src/test/java/me/siyoon/stockfilter/resource/fnguide/fnguide_main_sample.html");
        MAIN_PAGE = Jsoup.parse(Files.readString(sampleHtml.toPath()));
    }

    @ParameterizedTest
    @MethodSource
    void netIncome_test(Period period, NetIncome expected) {
        // given
        CrawledData crawledData = CrawledData.builder()
                                             .mainPage(MAIN_PAGE)
                                             .build();

        // when
        NetIncome netIncome = dut.netIncome(crawledData, period);

        // then
        assertThat(netIncome).isEqualTo(expected);
    }

    private static Stream<Arguments> netIncome_test() {
        return Stream.of(
                Arguments.of(Period.LAST_YEAR, NetIncome.from(855.0)),
                Arguments.of(Period.THIS_YEAR_EXPECTED, NetIncome.from(989.0)),
                Arguments.of(Period.LAST_QUARTER, NetIncome.from(91.0)),
                Arguments.of(Period.THIS_QUARTER_EXPECTED, NetIncome.from(369.0))
        );
    }
}