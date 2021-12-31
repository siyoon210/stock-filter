package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.domain.TradingInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TradingInfoExtractorTest {

    private static Document NAVER_MAIN_PAGE;

    @BeforeAll
    static void setUp() throws IOException {
        File sampleHtml = new File(
                "src/test/java/me/siyoon/stockfilter/resource/naver/naver-main-sample.html");
        NAVER_MAIN_PAGE = Jsoup.parse(Files.readString(sampleHtml.toPath()));
    }

    @Test
    void tradingInfo_test() {
        // given
        CrawledData crawledData = CrawledData.builder()
                                             .naverMainPage(NAVER_MAIN_PAGE)
                                             .build();
        // when
        TradingInfo actual = TradingInfoExtractor.tradingInfo(crawledData);

        // then
        assertThat(actual.price).isEqualTo(55800);
        assertThat(actual.tradingVolume).isEqualTo(58892L);
    }
}