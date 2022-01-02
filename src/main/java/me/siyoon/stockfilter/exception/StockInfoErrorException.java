package me.siyoon.stockfilter.exception;

public class StockInfoErrorException extends RuntimeException {

    public StockInfoErrorException() {
    }

    public StockInfoErrorException(String message) {
        super(message);
    }
}
