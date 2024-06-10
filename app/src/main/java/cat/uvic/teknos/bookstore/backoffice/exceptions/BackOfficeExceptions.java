package cat.uvic.teknos.bookstore.backoffice.exceptions;

public class BackOfficeExceptions extends RuntimeException {
    public BackOfficeExceptions() {
    }

    public BackOfficeExceptions(String message) {
        super(message);
    }

    public BackOfficeExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public BackOfficeExceptions(Throwable cause) {
        super(cause);
    }

    public BackOfficeExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
