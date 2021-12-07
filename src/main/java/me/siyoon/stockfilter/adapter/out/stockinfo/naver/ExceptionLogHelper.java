package me.siyoon.stockfilter.adapter.out.stockinfo.naver;

import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionLogHelper {

    private static final Set<String> DONT_CARE_MESSAGES = Set.of("empty String",
                                                                 "For input string: \"-\"",
                                                                 "실적 분석 테이블 파싱 실패 Index 0 out of bounds for length 0");

    public static void logParseException(String className, Exception ex) {
        String message = ex.getMessage();
        if (DONT_CARE_MESSAGES.contains(message)) {
            return;
        }
        log.warn("{} {}", className, message);
    }
}
