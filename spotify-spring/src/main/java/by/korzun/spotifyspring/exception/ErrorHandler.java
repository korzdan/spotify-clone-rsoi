package by.korzun.spotifyspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(SystemSettingIncorrectValueException.class)
    public ResponseEntity<String> handleSystemSettingIncorrectValueException(SystemSettingIncorrectValueException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SystemSettingTypeMismatchException.class)
    public ResponseEntity<String> handleSystemSettingTypeMismatchException(SystemSettingTypeMismatchException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GenreNotFound.class)
    public ResponseEntity<String> handleNotGenreFound(GenreNotFound e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
