package pt.insuranced.sdk.exceptions;

public class InsuranceDException extends Exception {
    public InsuranceDException() {
    }

    public InsuranceDException(String message) {
        super(message);
    }

    public InsuranceDException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsuranceDException(Throwable cause) {
        super(cause);
    }

    public InsuranceDException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
