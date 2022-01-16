package me.siyoon.stockfilter.adapter.out.stockcode.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockCodeFileRepository {

    private static final String FILE_PATH = "src/main/resources/data/";
    private static final String FILE_NAME = "stockCodes";

    public StockCodeFileRepository() {
        File baseDirectory = new File(FILE_PATH);
        if (!baseDirectory.exists()) {
            baseDirectory.mkdirs();
        }
    }

    public void save(List<String> stockCodes) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH + FILE_NAME);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(stockCodes);
            log.debug("stockCodes 쓰기 성공.");
        } catch (Exception ex) {
            log.debug("stockCodes 쓰기 실패.", ex);
        }
    }

    public List<String> find() {
        try (FileInputStream fileInput = new FileInputStream(FILE_PATH + FILE_NAME);
                ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {

            List<String> stockCodes = (List<String>) objectInput.readObject();
            log.debug("stockCodes 읽기 성공.");
            return stockCodes;
        } catch (Exception e) {
            log.debug("stockCodes 읽기 실패.");
            return Collections.emptyList();
        }
    }
}
