package by.korzun.spotifyspring.exception;

public class NotGenreFound extends RuntimeException {
    public NotGenreFound(String message) {
        super(message);
    }
}
