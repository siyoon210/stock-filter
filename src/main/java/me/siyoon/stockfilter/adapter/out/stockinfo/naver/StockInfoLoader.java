package me.siyoon.stockfilter.adapter.out.stockinfo.naver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockcode.StockCodeReader;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance2.PerformanceParser;
import me.siyoon.stockfilter.application.port.out.LoadStockInfoPort;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.exception.StockInfoConnectException;
import me.siyoon.stockfilter.exception.StockInfoParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockInfoLoader implements LoadStockInfoPort {

    public static final String URL = "https://finance.naver.com/item/main.nhn?code=";

    private final StockCodeReader stockCodeReader;
    private final NaverStockInfoParser stockInfoParser;
    private final NaverTradingInfoParser tradingInfoParser;
    private final PerformanceParser performanceParser2;

    @Override
    public List<StockInfo> loadedStockInfos() {
        List<String> stockCodes = stockCodeReader.stockCodes();
        return stockInfos(stockCodes);
    }

    private List<StockInfo> stockInfos(List<String> stockCodes) {
        WebDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME, true);
        try {
            // 네이버 페이지 가져오기,
            // ex) 종목 메인페이지, financial summary 연간 & 분기 페이지, 안정성 재무 연간 & 분기 페이지
            // 페이지 -> 도메인으로 변경하기
            return stockCodes.parallelStream()
                             .map(stockCode -> stockInfo(stockCode, driver))
                             .filter(Objects::nonNull)
                             .collect(Collectors.toList());
        } finally {
            driver.quit();
        }
    }

    private StockInfo stockInfo(String stockCode, WebDriver driver) {
        try {
            log.info("크롤링 시작. URL = {}", StockInfoLoader.URL + stockCode);
            Document document = document(stockCode);

            return StockInfo.builder()
                            .name(stockInfoParser.companyName(document))
                            .code(stockCode)
                            .tradingInfo(tradingInfoParser.tradingInfo(document))
                            .performances2(performanceParser2.performances(driver, stockCode))
                            .build();
        } catch (StockInfoParseException e) {
            ExceptionLogHelper.logParseException(StockInfoLoader.class.getSimpleName(), e);
            return null;
        }
    }

    private static Document document(String stockCode) {
        String stockInfoUrl = URL + stockCode;
        try {
            return Jsoup.connect(stockInfoUrl).get();
        } catch (IOException e) {
            throw new StockInfoConnectException("Connect 실패 : " + stockInfoUrl);
        }
    }
}
