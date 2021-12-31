package me.siyoon.stockfilter.application.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.application.port.out.StockInfoRepositoryPort;
import me.siyoon.stockfilter.application.port.out.StockInfoRetrievePort;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockInfoReader {
    private List<StockInfo> savedStockInfos = Collections.synchronizedList(new ArrayList<>());

    private final StockInfoRepositoryPort stockInfoRepositoryPort;
    private final StockInfoRetrievePort stockInfoRetrievePort;

    public synchronized List<StockInfo> stockInfos() {
        if (savedStockInfos.isEmpty()) {
            fetchStockInfos();
        }

        return savedStockInfos;
    }

    private void fetchStockInfos() {
        List<StockInfo> stockInfosFromRepository = stockInfoRepositoryPort.findAll();
        if (!stockInfosFromRepository.isEmpty()) {
            savedStockInfos = stockInfosFromRepository;
            return;
        }

        savedStockInfos = stockInfoRetrievePort.loadedStockInfos();
        stockInfoRepositoryPort.save(savedStockInfos);
    }
}
