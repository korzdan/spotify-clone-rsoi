package by.korzun.spotifyspring.service;

import by.korzun.spotifyspring.domain.Track;
import by.korzun.spotifyspring.exception.GenreNotFound;
import by.korzun.spotifyspring.repository.TrackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultTrackServiceTest {

    @Mock
    private TrackRepository trackRepository;
    @InjectMocks
    private DefaultTrackService trackService;

    private final static String GENRE = "ROCK";
    private final static String NONEXISTENT_GENRE = "gewgb";

    @Test
    public void returnTracks_WhenGenreExists() {
        when(trackRepository.findTracksByGenre(GENRE))
                .thenReturn(List.of(new Track()));

        assertEquals(1, trackService.findAllTracksByGenre(GENRE).size());
    }

    @Test
    public void throwNotGenreFound_WhenGenreNonexistent() {
        assertThrows(GenreNotFound.class, () -> trackService.findAllTracksByGenre(NONEXISTENT_GENRE));
    }

}
