package me.siyoon.stockfilter.application.port.out;

import java.util.List;
import me.siyoon.stockfilter.domain.StockInfo;

public interface LoadStockInfoPort {

    List<StockInfo> stockInfos();
}
