package me.siyoon.stockfilter.adapter.out.stockinfo;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockcode.StockCodeReader;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.repository.CrawledDataFileRepository;
import me.siyoon.stockfilter.adapter.out.stockinfo.data_crawler.StockInfoDataCrawler;
import me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.StockInfoExtractor;
import me.siyoon.stockfilter.application.port.out.StockInfoRetrievePort;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockInfoRetrieveProcessor implements StockInfoRetrievePort {

    private final StockCodeReader stockCodeReader;
    private final CrawledDataFileRepository crawledDataFileRepository;
    private final StockInfoDataCrawler stockInfoDataCrawler;
    private final StockInfoExtractor stockInfoExtractor;

    @Override
    public List<StockInfo> loadedStockInfos() {
        List<String> stockCodes = stockCodeReader.stockCodes();
        List<CrawledData> crawledDatas = crawledDatas(stockCodes);
        return stockInfoExtractor.stockInfosFrom(crawledDatas);
    }

    public List<CrawledData> crawledDatas(List<String> stockCodes) {
        log.info("CrawledData 불러오기 시작.");
        List<CrawledData> crawledDataFromFile = crawledDataFileRepository.findAll(stockCodes);
        if (crawledDataFromFile.isEmpty()) {
            List<CrawledData> crawledDatas = stockInfoDataCrawler.crawledDatas(stockCodes);
            crawledDataFileRepository.saveAll(crawledDatas);
            return crawledDatas;
        }
        checkSize(stockCodes, crawledDataFromFile);
        log.info("crawledDataFromFile 읽어오기 성공. size={}", crawledDataFromFile.size());
        return crawledDataFromFile;
    }

    private void checkSize(List<String> stockCodes, List<CrawledData> crawledDataFromFile) {
        if (crawledDataFromFile.size() != stockCodes.size()) {
            log.warn("crawledDataFromFile 와 stockCodes 데이터 사이즈가 다름. {} != {}",
                     crawledDataFromFile.size(), stockCodes.size());
        }
    }
}
