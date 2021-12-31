package me.siyoon.stockfilter.application.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import me.siyoon.stockfilter.adapter.out.repository.StockInfoFileRepository;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.TradingInfo;
import me.siyoon.stockfilter.domain.performance.Performance;
import me.siyoon.stockfilter.domain.performance.Performances;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class StockInfoFileRepositoryTest {

    @InjectMocks
    private StockInfoFileRepository dut;

    @Disabled
    @Test
    void write_test() {
        List<StockInfo> stockInfos = stockInfos();
        dut.save(stockInfos);
    }

    private List<StockInfo> stockInfos() {
        return Collections.singletonList(StockInfo.builder()
                                                  .name("name")
                                                  .code("123456")
                                                  .tradingInfo(tradingInfo())
                                                  .performances(performances())
                                                  .build());
    }

    private TradingInfo tradingInfo() {
        return TradingInfo.builder()
                          .price(1000.0)
                          .build();
    }

    private Performances performances() {
        return Performances.builder()
                           .value(Map.of(
                                   Period.LAST_YEAR,
                                   Performance.UNKNOWN_VALUE))
                           .build();
    }

    @Disabled
    @Test
    void read_test() {
        List<StockInfo> read = dut.findAll();
        System.out.println(read);
    }
}