package code2t.com.dvp.exception;

import java.time.Duration;

public final class RetryExhaustedException extends RuntimeException {

    public RetryExhaustedException(int attempts, Duration duration, Throwable cause) {
        super(String.format("Operation failed after %d attempts (%.2f seconds) Last error: %s",
                        attempts,
                        duration.toMillis() / 1000.0,
                        cause != null ? cause.getMessage() : "unknown"
                ),
                cause
        );
    }
}