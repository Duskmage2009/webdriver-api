package executor.api.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ResourceNotFoundException extends ResponseStatusException {

    private static final String DEFAULT_REASON = "Resource not found";

    public ResourceNotFoundException() {
        this(HttpStatus.NOT_FOUND, DEFAULT_REASON);
    }

    public ResourceNotFoundException(String reason) {
        this(HttpStatus.NOT_FOUND, reason);
    }

    private ResourceNotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
