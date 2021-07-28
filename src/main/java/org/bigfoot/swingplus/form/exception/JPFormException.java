package org.bigfoot.swingplus.form.exception;

/**
 * @author Jonathan la Roi
 * @since 8/28/2020
 */
public class JPFormException extends RuntimeException {

    public JPFormException(Exception exception) {
        super(exception);
    }

    public JPFormException(String msg) {
        super(msg);
    }

    public JPFormException(String msg, Exception exception) {
        super(msg, exception);
    }
}
