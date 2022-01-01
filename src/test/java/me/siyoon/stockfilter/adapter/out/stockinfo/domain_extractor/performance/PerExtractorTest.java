package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.PER;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class PerExtractorTest {

    private static Document FN_GUIDE_MAIN_PAGE;

    @BeforeAll
    static void setUp() throws IOException {
        File sampleHtml = new File(
                "src/test/java/me/siyoon/stockfilter/resource/fnguide/fnguide-main-sample.html");
        FN_GUIDE_MAIN_PAGE = Jsoup.parse(Files.readString(sampleHtml.toPath()));
    }

    @ParameterizedTest
    @MethodSource
    void per_test(Period period, PER expected) {
        // given
        CrawledData crawledData = CrawledData.builder()
                                             .fnGuideMainPage(FN_GUIDE_MAIN_PAGE)
                                             .build();

        // when
        PER per = PerExtractor.per(crawledData, period);

        // then
        assertThat(per).isEqualTo(expected);
    }

    private static Stream<Arguments> per_test() {
        return Stream.of(
                Arguments.of(Period.LAST_YEAR, PER.from(22.22)),
                Arguments.of(Period.THIS_YEAR_EXPECTED, PER.from(17.84)),
                Arguments.of(Period.LAST_QUARTER, PER.UNKNOWN_VALUE),
                Arguments.of(Period.THIS_QUARTER_EXPECTED, PER.UNKNOWN_VALUE)
        );
    }
}