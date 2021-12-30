package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.QuickRatio;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class QuickRatioExtractorTest {

    private static Document NAVER_MAIN_PAGE;

    @BeforeAll
    static void setUp() throws IOException {
        File sampleHtml = new File(
                "src/test/java/me/siyoon/stockfilter/resource/naver/naver-main-sample.html");
        NAVER_MAIN_PAGE = Jsoup.parse(Files.readString(sampleHtml.toPath()));
    }

    @ParameterizedTest
    @MethodSource
    void quickRatio_test(Period period, QuickRatio expected) {
        // given
        CrawledData crawledData = CrawledData.builder()
                                             .naverMainPage(NAVER_MAIN_PAGE)
                                             .build();

        // when
        QuickRatio actual = QuickRatioExtractor.quickRatio(crawledData, period);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> quickRatio_test() {
        return Stream.of(
                Arguments.of(Period.LAST_YEAR, QuickRatio.from(196.96)),
                Arguments.of(Period.THIS_YEAR_EXPECTED, QuickRatio.UNKNOWN_VALUE),
                Arguments.of(Period.LAST_QUARTER, QuickRatio.from(176.42)),
                Arguments.of(Period.THIS_QUARTER_EXPECTED, QuickRatio.UNKNOWN_VALUE)
        );
    }
}