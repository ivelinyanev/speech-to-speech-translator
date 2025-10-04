package org.example.translator.exceptions;

import java.io.IOException;

import com.deepl.api.DeepLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.example.translator.helpers.StringConstants.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException e) {
        logger.error(IO_ERROR, e);

        ErrorResponse error = new ErrorResponse(
                IO_FILE_PROCESS_ERROR + e.getMessage(),
                "I/O Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<ErrorResponse> handleInterruptedException(InterruptedException e) {
        logger.error(INTERRUPTION_ERROR, e);

        ErrorResponse error = new ErrorResponse(
                TEXT_TRANSLATION_ERROR_MESSAGE + e.getMessage(),
                "Translation Interrupted",
                HttpStatus.SERVICE_UNAVAILABLE.value()
        );
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(error);
    }

    @ExceptionHandler(DeepLException.class)
    public ResponseEntity<ErrorResponse> handleDeepLException(DeepLException e) {
        logger.error(DEEPL_ERROR, e);

        ErrorResponse error = new ErrorResponse(
                DEEPL_STANDARD_ERROR_MESSAGE + ": " + e.getMessage(),
                "DeepL API Error",
                HttpStatus.BAD_GATEWAY.value()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        logger.error(UNEXPECTED_ERROR_OCCURRED, e);

        ErrorResponse error = new ErrorResponse(
                UNEXPECTED_SERVER_ERROR + e.getMessage(),
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}