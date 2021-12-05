package me.siyoon.stockfilter.adapter.out;

import java.util.List;
import me.siyoon.stockfilter.application.port.out.LoadStockInfoPort;
import me.siyoon.stockfilter.domain.StockInfo;

public class NaverStockInfoCrawler implements LoadStockInfoPort {

    @Override
    public List<StockInfo> stockInfos() {
        return null;
    }
}
