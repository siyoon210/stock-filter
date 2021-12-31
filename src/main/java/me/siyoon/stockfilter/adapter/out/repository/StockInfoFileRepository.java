package me.siyoon.stockfilter.adapter.out.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.application.port.out.StockInfoRepositoryPort;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockInfoFileRepository implements StockInfoRepositoryPort {

    private static final String FILE_PATH = "src/main/resources/data/stockInfos.txt";

    public void save(List<StockInfo> stockInfos) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(stockInfos);
            log.info("StockInfoFile 쓰기 성공. size=" + stockInfos.size());
        } catch (Exception ex) {
            log.warn("StockInfoFile 쓰기 실패.");
        }
    }

    public List<StockInfo> findAll() {
        try (FileInputStream fileInput = new FileInputStream(FILE_PATH);
             ObjectInputStream objectInput = new ObjectInputStream(fileInput)){

            return (List<StockInfo>) objectInput.readObject();
        } catch (Exception e) {
            log.warn("StockInfoFile 읽기 실패.");
            return Collections.emptyList();
        }
    }
}
