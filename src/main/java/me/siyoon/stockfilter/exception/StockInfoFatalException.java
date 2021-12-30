package me.siyoon.stockfilter.exception;

public class StockInfoFatalException extends RuntimeException {

    public StockInfoFatalException() {
    }

    public StockInfoFatalException(String message) {
        super(message);
    }
}
