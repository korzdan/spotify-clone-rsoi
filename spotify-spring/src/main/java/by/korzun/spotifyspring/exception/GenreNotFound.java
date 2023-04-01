package by.korzun.spotifyspring.exception;

public class GenreNotFound extends RuntimeException {
    public GenreNotFound(String message) {
        super(message);
    }
}
