package by.korzun.spotifyspring.exception;

public class SystemSettingIncorrectValueException extends RuntimeException {
    public SystemSettingIncorrectValueException(String message) {
        super(message);
    }
}
