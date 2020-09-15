package io.cucumber.pool;

public class EmptyPoolException extends RuntimeException {

    public EmptyPoolException(String message) {
        super(message);
    }
}
