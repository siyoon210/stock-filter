package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.exception.StockInfoFatalException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerformanceExceptionHandler {

    public static Object handle(RuntimeException e, String stockCode, Period period,
                                Object fallBackValue) {
        String elementClassName = fallBackValue.getClass().getSimpleName();

        if (e instanceof StockInfoFatalException) {
            String errorMessage = String.format(
                    "%s StockInfoFatalException. stockCode:%s, period:%s",
                    elementClassName, stockCode, period);
            throw new StockInfoFatalException(errorMessage);
        }

        if (e instanceof NullPointerException) {
            log.info("{} NPE stockCode: {}, period: {}",
                     elementClassName, stockCode, period);
            return fallBackValue;
        }

        log.warn("{} stockCode: {}, period: {}",
                 elementClassName, stockCode, period, e);
        return fallBackValue;
    }
}
