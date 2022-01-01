package me.siyoon.stockfilter.adapter.aop;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Around("execution(public * me.siyoon.stockfilter.adapter.out.stockcode.*.stockCodes())")
    public Object loggingStockCode(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Stock Code 추출 시작.");
        long startAt = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endAt = System.currentTimeMillis();
        log.info("Stock Code 추출 끝. size: {} ({}ms)", ((List<String>) result).size(),
                 endAt - startAt);
        return result;
    }

    @Around("execution(public * me.siyoon.stockfilter.adapter.out.stockinfo.StockInfoRetrieveProcessor.loadedStockInfos())")
    public Object loggingStockInfo(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Stock Info 로드 시작.");
        long startAt = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endAt = System.currentTimeMillis();
        log.info("Stock Info 로드 끝. size: {} ({}ms)", ((List<String>) result).size(),
                 endAt - startAt);
        return result;
    }
}
