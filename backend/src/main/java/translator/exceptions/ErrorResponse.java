package translator.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    private final String message;
    private final String error;
    private final int status;
    private final LocalDateTime timestamp;

    public ErrorResponse(String message, String error, int status) {
        this.message = message;
        this.error = error;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

}
