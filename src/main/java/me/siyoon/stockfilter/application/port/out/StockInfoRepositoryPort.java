package me.siyoon.stockfilter.application.port.out;

import java.util.List;
import me.siyoon.stockfilter.domain.StockInfo;

public interface StockInfoRepositoryPort {

    void save(List<StockInfo> stockInfos);

    List<StockInfo> findAll();
}
