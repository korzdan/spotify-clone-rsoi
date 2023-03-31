package by.korzun.spotifyspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(SystemSettingIncorrectValueException.class)
    public ResponseEntity<String> handleInvalidFileException(SystemSettingIncorrectValueException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SystemSettingTypeMismatchException.class)
    public ResponseEntity<String> handleFileParserException(SystemSettingTypeMismatchException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

}
