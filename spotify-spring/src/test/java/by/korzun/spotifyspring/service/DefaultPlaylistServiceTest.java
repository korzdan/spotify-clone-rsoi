package by.korzun.spotifyspring.service;

import by.korzun.spotifyspring.domain.Playlist;
import by.korzun.spotifyspring.domain.Track;
import by.korzun.spotifyspring.domain.TrackHistory;
import by.korzun.spotifyspring.repository.TrackRepository;
import by.korzun.spotifyspring.system_settigns.SystemSettingsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static by.korzun.spotifyspring.service.PlaylistConstants.TOP_TRACKS_OF_MONTH_PLAYLIST_NAME;
import static by.korzun.spotifyspring.service.PlaylistConstants.TOP_TRACKS_OF_PREVIOUS_MONTH_PLAYLIST_NAME;
import static java.time.temporal.ChronoUnit.MONTHS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultPlaylistServiceTest {

    @Mock
    private TrackRepository trackRepository;
    @Mock
    private SystemSettingsService systemSettingsService;
    @InjectMocks
    private DefaultPlaylistService defaultPlaylistService;

    private static List<Track> ALL_TRACKS;

    @BeforeAll
    public static void init() {
        ALL_TRACKS = List.of(
                new Track()
                        .setName("Track 1")
                        .setIsBlocked(false)
                        .setIsLiked(false)
                        .setPlayHistory(List.of(
                                new TrackHistory(null, null, LocalDateTime.now()),
                                new TrackHistory(null, null, LocalDateTime.now()),
                                new TrackHistory(null, null, LocalDateTime.now()),
                                new TrackHistory(null, null, LocalDateTime.now().minus(1, MONTHS)),
                                new TrackHistory(null, null, LocalDateTime.now().minus(1, MONTHS))
                        )),
                new Track()
                        .setName("Track 2")
                        .setIsBlocked(false)
                        .setIsLiked(false)
                        .setPlayHistory(List.of(
                                new TrackHistory(null, null, LocalDateTime.now().minus(1, MONTHS)),
                                new TrackHistory(null, null, LocalDateTime.now().minus(1, MONTHS))
                        )),
                new Track()
                        .setName("Track 3")
                        .setIsBlocked(false)
                        .setIsLiked(false)
                        .setPlayHistory(List.of(
                                new TrackHistory(null, null, LocalDateTime.now()),
                                new TrackHistory(null, null, LocalDateTime.now().minus(1, MONTHS))
                        )),
                new Track()
                        .setName("Track 4")
                        .setIsBlocked(true)
                        .setIsLiked(false)
                        .setPlayHistory(List.of(
                                new TrackHistory(null, null, LocalDateTime.now()),
                                new TrackHistory(null, null, LocalDateTime.now().minus(1, MONTHS))
                        )),
                new Track()
                        .setName("Track 4")
                        .setIsBlocked(true)
                        .setIsLiked(false)
                        .setPlayHistory(List.of(
                                new TrackHistory(null, null, LocalDateTime.now()),
                                new TrackHistory(null, null, LocalDateTime.now().minus(1, MONTHS))
                        ))
        );
    }

    @Test
    void getPlaylistOfTopTracksOfThisMonth() {
        when(trackRepository.findAll())
                .thenReturn(ALL_TRACKS);
        when(systemSettingsService.findNumberOfTracksInPlaylist())
                .thenReturn(1);
        Playlist playlist = defaultPlaylistService.getPlaylistOfTopTracksOfMonth();

        assertEquals(1L, playlist.getTracks().size());
        assertEquals(TOP_TRACKS_OF_MONTH_PLAYLIST_NAME, playlist.getName());
        assertEquals("Track 1", playlist.getTracks().get(0).getName());
    }

    @Test
    void getPlaylistOfTopTracksOfPreviousMonth() {
        when(trackRepository.findAll())
                .thenReturn(ALL_TRACKS);
        when(systemSettingsService.findNumberOfTracksInPlaylist())
                .thenReturn(2);
        Playlist playlist = defaultPlaylistService.getPlaylistOfTopTracksOfPreviousMonth();

        assertEquals(2L, playlist.getTracks().size());
        assertEquals(TOP_TRACKS_OF_PREVIOUS_MONTH_PLAYLIST_NAME, playlist.getName());
        assertEquals("Track 2", playlist.getTracks().get(0).getName());
        assertNotEquals("Track 1", playlist.getTracks().get(1).getName());
    }
}
