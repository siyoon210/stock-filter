package me.siyoon.stockfilter.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.siyoon.stockfilter.exception.StockInfoConnectException;
import me.siyoon.stockfilter.exception.StockInfoErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StockFilterControllerAdvice {

    @ExceptionHandler(StockInfoConnectException.class)
    public ResponseEntity<Object> handleServerError(RuntimeException e) {
        return ResponseEntity.internalServerError()
                             .body(new Response(e.getMessage()));
    }

    @ExceptionHandler(StockInfoErrorException.class)
    public ResponseEntity<Object> handleFatalException(RuntimeException e) {
        return ResponseEntity.internalServerError()
                             .body(new Response(e.getMessage()));
    }

    @RequiredArgsConstructor
    @Getter
    private static class Response {

        private final String message;
    }
}
