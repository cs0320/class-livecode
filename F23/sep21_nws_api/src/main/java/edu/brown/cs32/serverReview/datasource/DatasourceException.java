package edu.brown.cs32.serverReview.datasource;

public class DatasourceException extends Exception {
    private final Throwable cause;

    public DatasourceException(String message) {
        super(message);
        this.cause = null;
    }
    public DatasourceException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }
}
