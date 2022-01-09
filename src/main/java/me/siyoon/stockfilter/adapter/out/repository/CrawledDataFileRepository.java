package me.siyoon.stockfilter.adapter.out.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CrawledDataFileRepository {

    //todo PostConstructor data/crawledData/ 디렉토리 없으면 생성하기

    private static final String FILE_PATH = "src/main/resources/data/crawledData/";

    public void saveAll(List<CrawledData> crawledDatas) {
        crawledDatas.parallelStream()
                    .forEach(this::save);
    }

    private void save(CrawledData crawledData) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH + crawledData.stockCode);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(SerializableCrawledData.from(crawledData));
            log.debug("SerializableCrawledData 쓰기 성공. stockCode={}", crawledData.stockCode);
        } catch (Exception ex) {
            log.debug("SerializableCrawledData 쓰기 실패. stockCode={}", crawledData.stockCode, ex);
        }
    }

    public List<CrawledData> findAll(List<String> stockCodes) {
        return stockCodes.stream()
                         .map(this::find)
                         .filter(crawledData -> crawledData != CrawledData.EMPTY)
                         .collect(Collectors.toList());
    }

    private CrawledData find(String stockCode) {
        try (FileInputStream fileInput = new FileInputStream(FILE_PATH + stockCode);
                ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {

            SerializableCrawledData serializableCrawledData = (SerializableCrawledData) objectInput.readObject();
            log.debug("SerializableCrawledData 읽기 성공. stockCode={}", stockCode);
            return serializableCrawledData.toCrawledData();
        } catch (Exception e) {
            log.debug("SerializableCrawledData 읽기 실패. stockCode={}", stockCode);
            return CrawledData.EMPTY;
        }
    }
}
