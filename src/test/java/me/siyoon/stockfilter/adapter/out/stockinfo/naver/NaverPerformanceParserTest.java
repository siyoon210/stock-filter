package me.siyoon.stockfilter.adapter.out.stockinfo.naver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import me.siyoon.stockfilter.domain.DPS;
import me.siyoon.stockfilter.domain.NetIncome;
import me.siyoon.stockfilter.domain.Performances;
import me.siyoon.stockfilter.domain.Period;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class NaverPerformanceParserTest {

    @InjectMocks
    private NaverPerformanceParser dut;

    private Document document;

    @BeforeEach
    void setUp() throws IOException {
        File sampleHtml = new File(
                "src/test/java/me/siyoon/stockfilter/resource/naver/sample-stock-info.html");
        document = Jsoup.parse(Files.readString(sampleHtml.toPath()));
    }

    @Test
    void performances_test() {
        // given

        // when
        Performances performances = dut.performances(document);

        // then
        assertThat(performances.of(Period.LAST_YEAR).netIncome).isEqualTo(new NetIncome(1286.0));
        assertThat(performances.of(Period.THIS_YEAR_EXPECTED).dps).isEqualTo(new DPS(454.0));
    }
}