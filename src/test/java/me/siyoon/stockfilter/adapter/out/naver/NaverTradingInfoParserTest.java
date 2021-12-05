package me.siyoon.stockfilter.adapter.out.naver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import me.siyoon.stockfilter.domain.TradingInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class NaverTradingInfoParserTest {

    @InjectMocks
    private NaverTradingInfoParser dut;

    private Document document;

    @BeforeEach
    void setUp() throws IOException {
        File sampleHtml = new File("src/test/java/me/siyoon/stockfilter/resource/naver/sample-stock-info.html");
        document = Jsoup.parse(Files.readString(sampleHtml.toPath()));
    }

    @Test
    void tradingInfo_test() {
        // given
        // when
        TradingInfo actual = dut.tradingInfo(document);

        // then
        assertThat(actual.price).isEqualTo(6930);
        assertThat(actual.tradingVolume).isEqualTo(168894L);
    }
}