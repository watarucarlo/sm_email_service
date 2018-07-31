package com.au.siteminder.framework.exception;

/**
 * Application level exception
 */
public class SiteminderServicesException extends RuntimeException {

    public SiteminderServicesException() {
        super();
    }

    public SiteminderServicesException(String message, Throwable cause) {
        super(message, cause);
    }

    public SiteminderServicesException(String message) {
        super(message);
    }

    public SiteminderServicesException(Throwable cause) {
        super(cause);
    }

}
