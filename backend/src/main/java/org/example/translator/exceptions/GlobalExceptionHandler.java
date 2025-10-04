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
    public ResponseEntity<String> handleIOException(IOException e) {
        logger.error(IO_LOG_ERROR, e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(IO_FILE_PROCESS_ERROR + e.getMessage());
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<String> handleInterruptedException(InterruptedException e) {
        logger.error(INTERRUPTION_ERROR, e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(TEXT_TRANSLATION_ERROR_MESSAGE + e.getMessage());
    }

    @ExceptionHandler(DeepLException.class)
    public ResponseEntity<String> handleDeepLException(DeepLException e) {
        logger.error(DEEPL_ERROR, e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(DEEPL_STANDARD_ERROR_MESSAGE + e.getMessage());
    }
}