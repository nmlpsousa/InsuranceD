package pt.insuranced.sdk.exceptions;

public class InsuranceDException extends Exception {
    private static final long serialVersionUID = 1327752485427358673L;

    public InsuranceDException() {
        super();
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
