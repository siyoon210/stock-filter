package me.siyoon.stockfilter.adapter.out;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.siyoon.stockfilter.adapter.out.code.StockCodeReader;
import me.siyoon.stockfilter.application.port.out.LoadStockInfoPort;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NaverStockInfoCrawler implements LoadStockInfoPort {

    private final StockCodeReader stockCodeReader;

    @Override
    public List<StockInfo> stockInfos() {
        List<String> stockCodes = stockCodeReader.stockCodes();
        System.out.println("stockCodes = " + stockCodes);
        return null;
    }
}
