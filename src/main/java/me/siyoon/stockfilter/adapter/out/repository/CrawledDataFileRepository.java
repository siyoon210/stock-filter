package me.siyoon.stockfilter.adapter.out.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CrawledDataFileRepository {

    private static final String FILE_PATH = "src/main/resources/data/crawledData.txt";

    public void save(List<CrawledData> crawledData) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(serializableCrawledDatas(crawledData));
            log.info("SerializableCrawledData 쓰기 성공. size=" + crawledData.size());
        } catch (Exception ex) {
            log.warn("SerializableCrawledData 쓰기 실패.");
        }
    }

    private List<SerializableCrawledData> serializableCrawledDatas(List<CrawledData> crawledData) {
        return crawledData.stream()
                          .map(SerializableCrawledData::from)
                          .collect(Collectors.toList());
    }

    public List<CrawledData> findAll() {
        try (FileInputStream fileInput = new FileInputStream(FILE_PATH);
                ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {

            List<SerializableCrawledData> serializableCrawledData = (List<SerializableCrawledData>) objectInput.readObject();
            log.info("SerializableCrawledData 읽기 성공. size={}", serializableCrawledData.size());
            return crawledDatas(serializableCrawledData);
        } catch (Exception e) {
            log.warn("SerializableCrawledData 읽기 실패.");
            return Collections.emptyList();
        }
    }

    private List<CrawledData> crawledDatas(List<SerializableCrawledData> serializableCrawledData) {
        return serializableCrawledData.stream()
                                      .map(SerializableCrawledData::toCrawledData)
                                      .collect(Collectors.toList());
    }
}
