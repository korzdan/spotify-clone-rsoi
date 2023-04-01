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
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalDateTime;
import java.util.List;

import static by.korzun.spotifyspring.service.PlaylistConstants.*;
import static java.time.temporal.ChronoUnit.MONTHS;
import static org.junit.jupiter.api.Assertions.*;
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
    private static List<Track> LIKED_ONLY_TRACKS;

    @BeforeAll
    public static void init() {
        ALL_TRACKS = List.of(
                new Track()
                        .setId(1L)
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
                        .setId(2L)
                        .setName("Track 2")
                        .setIsBlocked(false)
                        .setIsLiked(false)
                        .setPlayHistory(List.of(
                                new TrackHistory(null, null, LocalDateTime.now().minus(1, MONTHS)),
                                new TrackHistory(null, null, LocalDateTime.now().minus(1, MONTHS))
                        )),
                new Track()
                        .setId(3L)
                        .setName("Track 3")
                        .setIsBlocked(false)
                        .setIsLiked(false)
                        .setPlayHistory(List.of(
                                new TrackHistory(null, null, LocalDateTime.now()),
                                new TrackHistory(null, null, LocalDateTime.now().minus(1, MONTHS))
                        )),
                new Track()
                        .setId(4L)
                        .setName("Track 4")
                        .setIsBlocked(false)
                        .setIsLiked(false)
                        .setPlayHistory(List.of(
                                new TrackHistory(null, null, LocalDateTime.now()),
                                new TrackHistory(null, null, LocalDateTime.now().minus(1, MONTHS))
                        )),
                new Track()
                        .setId(5L)
                        .setName("Track 5")
                        .setIsBlocked(true)
                        .setIsLiked(true)
                        .setPlayHistory(List.of(
                                new TrackHistory(null, null, LocalDateTime.now()),
                                new TrackHistory(null, null, LocalDateTime.now().minus(1, MONTHS)),
                                new TrackHistory(null, null, LocalDateTime.now().minus(1, MONTHS))
                        ))
        );
        LIKED_ONLY_TRACKS = List.of(
                new Track()
                        .setIsLiked(true)
                        .setIsBlocked(false)
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
        assertNotEquals("Track 5", playlist.getTracks().get(1).getName());
    }

    @Test
    void getMyTopTracks() {
        when(systemSettingsService.findNumberOfTracksInPlaylist())
                .thenReturn(1);
        when(trackRepository.findLikedTracks(1))
                .thenReturn(LIKED_ONLY_TRACKS);
        Playlist playlist = defaultPlaylistService.getMyTopTracks();

        assertEquals(1L, playlist.getTracks().size());
        assertEquals(MY_TOP_LIKED_TRACKS, playlist.getName());
        assertTrue(playlist.getTracks().get(0).getIsLiked());
        assertFalse(playlist.getTracks().get(0).getIsBlocked());
    }
}
