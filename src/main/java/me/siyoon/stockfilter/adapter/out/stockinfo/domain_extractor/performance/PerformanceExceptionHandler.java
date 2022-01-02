package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.exception.StockInfoErrorException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerformanceExceptionHandler {

    public static Object handle(RuntimeException e, String stockCode, Period period,
                                Object fallBackValue) {
        String elementClassName = fallBackValue.getClass().getSimpleName();

        if (e instanceof StockInfoErrorException) {
            String errorMessage = String.format(
                    "%s StockInfoErrorException. stockCode:%s, period:%s",
                    elementClassName, stockCode, period);
            throw new StockInfoErrorException(errorMessage);
        }

        if (e instanceof NullPointerException) {
            log.debug("{} NullPointerException stockCode: {}, period: {}",
                     elementClassName, stockCode, period);
            return fallBackValue;
        }

        if (e instanceof IndexOutOfBoundsException) {
            log.debug("{} IndexOutOfBoundsException stockCode: {}, period: {}",
                     elementClassName, stockCode, period);
            return fallBackValue;
        }

        log.warn("{} stockCode: {}, period: {}",
                 elementClassName, stockCode, period, e);
        return fallBackValue;
    }
}
