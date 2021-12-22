package me.siyoon.stockfilter.adapter.out.stockinfo.naver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockcode.StockCodeReader;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.financial.PerformanceParser;
import me.siyoon.stockfilter.application.port.out.LoadStockInfoPort;
import me.siyoon.stockfilter.domain.StockInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockInfoCrawler implements LoadStockInfoPort {

    private final StockCodeReader stockCodeReader;
    private final PerformanceParser performanceParser;

    @Override
    public List<StockInfo> loadedStockInfos() {
        List<String> stockCodes = stockCodeReader.stockCodes();
        WebDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME, true);
        try {
            return stockCodes.stream()
                        .map(code -> stockInfo(driver, code))
                        .collect(Collectors.toList());
        } finally {
            driver.quit();
        }
    }

    private StockInfo stockInfo(WebDriver driver, String code) {
        return StockInfo.builder()
                        .performances2(performanceParser.performances(driver, code))
                        .build();
    }
}
