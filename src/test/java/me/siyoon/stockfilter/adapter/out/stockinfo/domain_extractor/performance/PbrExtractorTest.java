package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.PBR;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class PbrExtractorTest {

    private static Document FN_GUIDE_MAIN_PAGE;

    @BeforeAll
    static void setUp() throws IOException {
        File sampleHtml = new File(
                "src/test/java/me/siyoon/stockfilter/resource/fnguide/fnguide-main-sample.html");
        FN_GUIDE_MAIN_PAGE = Jsoup.parse(Files.readString(sampleHtml.toPath()));
    }

    @ParameterizedTest
    @MethodSource
    void pbr_test(Period period, PBR expected) {
        // given
        CrawledData crawledData = CrawledData.builder()
                                             .fnGuideMainPage(FN_GUIDE_MAIN_PAGE)
                                             .build();

        // when
        PBR pbr = PbrExtractor.pbr(crawledData, period);

        // then
        assertThat(pbr).isEqualTo(expected);
    }

    private static Stream<Arguments> pbr_test() {
        return Stream.of(
                Arguments.of(Period.LAST_YEAR, PBR.from(1.32)),
                Arguments.of(Period.THIS_YEAR_EXPECTED, PBR.from(1.14)),
                Arguments.of(Period.LAST_QUARTER, PBR.from(1.36)),
                Arguments.of(Period.THIS_QUARTER_EXPECTED, PBR.UNKNOWN_VALUE)
        );
    }
}