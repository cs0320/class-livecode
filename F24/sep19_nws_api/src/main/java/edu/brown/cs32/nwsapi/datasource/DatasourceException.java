package edu.brown.cs32.nwsapi.datasource;

/**
 * This exception communicates that something went wrong with
 * a requested datasource. It _wraps_ the original cause as a
 * field, which helps with debugging, but also allows the caller
 * to handle the issue uniformly if they wish, without looking
 * inside.
 */
public class DatasourceException extends Exception {
    // The root cause of this datasource problem
    private final Throwable cause;

    public DatasourceException(String message) {
        super(message); // Exception message
        this.cause = null;
    }
    public DatasourceException(String message, Throwable cause) {
        super(message); // Exception message
        this.cause = cause;
    }

    /**
     * Returns the Throwable provided (if any) as the root cause of
     * this exception. We don't make a defensive copy here because
     * we don't anticipate mutation of the Throwable to be any issue,
     * and because this is mostly implemented for debugging support.
     * @return the root cause Throwable
     */
    public Throwable getCause() { return this.cause; }
}
