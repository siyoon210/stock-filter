package me.siyoon.stockfilter.application.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.application.port.out.StockInfoRetrievePort;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockInfoReader {
    private List<StockInfo> savedStockInfos = Collections.synchronizedList(new ArrayList<>());

    private final StockInfoRetrievePort stockInfoRetrievePort;

    public synchronized List<StockInfo> stockInfos() {
        if (savedStockInfos.isEmpty()) {
            savedStockInfos = stockInfoRetrievePort.loadedStockInfos();
        }

        return savedStockInfos;
    }
}
