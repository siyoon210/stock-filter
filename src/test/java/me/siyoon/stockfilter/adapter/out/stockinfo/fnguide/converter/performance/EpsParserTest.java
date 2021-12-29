package me.siyoon.stockfilter.adapter.out.stockinfo.fnguide.converter.performance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.EPS;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class EpsParserTest {

    private final EpsParser dut = new EpsParser();

    private static Document MAIN_PAGE;

    @BeforeAll
    static void setUp() throws IOException {
        File sampleHtml = new File("src/test/java/me/siyoon/stockfilter/resource/fnguide/fnguide_main_sample.html");
        MAIN_PAGE = Jsoup.parse(Files.readString(sampleHtml.toPath()));
    }

    @ParameterizedTest
    @MethodSource
    void eps_test(Period period, EPS expected) {
        // given
        CrawledData crawledData = CrawledData.builder()
                                             .mainPage(MAIN_PAGE)
                                             .build();

        // when
        EPS eps = dut.eps(crawledData, period);

        // then
        assertThat(eps).isEqualTo(expected);
    }

    private static Stream<Arguments> eps_test() {
        return Stream.of(
                Arguments.of(Period.LAST_YEAR, EPS.from(2839.0)),
                Arguments.of(Period.THIS_YEAR_EXPECTED, EPS.from(3196.0)),
                Arguments.of(Period.LAST_QUARTER, EPS.from(270.0)),
                Arguments.of(Period.THIS_QUARTER_EXPECTED, EPS.from(1119.0))
        );
    }
}