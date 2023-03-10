package by.korzun.spotifyspring.service;

import by.korzun.spotifyspring.domain.Playlist;
import by.korzun.spotifyspring.domain.Track;
import by.korzun.spotifyspring.domain.TrackHistory;
import by.korzun.spotifyspring.domain.dto.TrackPlays;
import by.korzun.spotifyspring.repository.PlaylistRepository;
import by.korzun.spotifyspring.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DefaultPlaylistService implements PlaylistService {

    private final TrackRepository trackRepository;
    private final PlaylistRepository playlistRepository;

    private static final Integer NUMBER_OF_TRACKS_IN_PLAYLIST = 3;
    private static final String TOP_TRACKS_OF_MONTH_PLAYLIST_NAME = "Top tracks of month";
    private static final String TOP_TRACKS_OF_PREVIOUS_MONTH_PLAYLIST_NAME = "Top tracks of previous month";
    private static final String RANDOM_PLAYLIST_NAME = "Random playlist";

    @Override
    public Playlist getPlaylistOfTopTracksOfMonth() {
        Playlist topTracksOfMonthPlaylist = playlistRepository.getPlaylistByName(TOP_TRACKS_OF_MONTH_PLAYLIST_NAME);
        return topTracksOfMonthPlaylist == null
                ? assembleAndSavePlaylistOfTopTracksOfMonth()
                : topTracksOfMonthPlaylist;
    }

    private Playlist assembleAndSavePlaylistOfTopTracksOfMonth() {
        return savePlaylist(TOP_TRACKS_OF_MONTH_PLAYLIST_NAME, getTopTracksOfMonth(YearMonth.now(), null));
    }

    @Override
    public Playlist getPlaylistOfTopTracksOfPreviousMonth() {
        Playlist topTracksOfPreviousMonth = playlistRepository
                .getPlaylistByName(TOP_TRACKS_OF_PREVIOUS_MONTH_PLAYLIST_NAME);
        return topTracksOfPreviousMonth == null
                ? assembleAndSavePlaylistOfTopTracksOfPreviousMonth(getPlaylistOfTopTracksOfMonth().getTracks())
                : topTracksOfPreviousMonth;

    }

    private Playlist assembleAndSavePlaylistOfTopTracksOfPreviousMonth(List<Track> tracksToExclude) {
        return savePlaylist(
                TOP_TRACKS_OF_PREVIOUS_MONTH_PLAYLIST_NAME,
                getTopTracksOfMonth(YearMonth.of(Year.now().getValue(),
                        YearMonth.now().getMonth().getValue() - 1), tracksToExclude)
        );
    }

    @Override
    public Playlist getRandomPlaylist() {
        Playlist randomPlaylist = playlistRepository.getPlaylistByName(RANDOM_PLAYLIST_NAME);
        return randomPlaylist == null
                ? assembleAndSaveRandomPlaylist()
                : randomPlaylist;
    }

    private Playlist assembleAndSaveRandomPlaylist() {
        List<Track> tracksFromFirstPlaylist = getPlaylistOfTopTracksOfMonth().getTracks();
        List<Track> tracksFromSecondPlaylist = getPlaylistOfTopTracksOfPreviousMonth().getTracks();
        List<Track> tracksToExclude = Stream
                .concat(tracksFromFirstPlaylist.stream(), tracksFromSecondPlaylist.stream()).toList();
        List<Track> randomTracks = List.of(
                tracksFromFirstPlaylist.get(0),
                tracksFromSecondPlaylist.get(0),
                getTopTracksOfMonth(YearMonth.now(), tracksToExclude).get(0)
        );
        return savePlaylist(RANDOM_PLAYLIST_NAME, randomTracks);
    }

    private List<Track> getTopTracksOfMonth(YearMonth month, List<Track> tracksToExclude) {
        return trackRepository.
                findAll().stream()
                .map(track -> new TrackPlays(track, countTrackPlaysOfMonth(month, track.getPlayHistory())))
                .sorted(Comparator.comparing(TrackPlays::getTimesPlayed).reversed())
                .map(TrackPlays::getTrack)
                .filter(track -> filterTrack(tracksToExclude, track))
                .limit(NUMBER_OF_TRACKS_IN_PLAYLIST.longValue())
                .toList();
    }

    private boolean filterTrack(List<Track> tracksToExclude, Track track) {
        return Optional.ofNullable(tracksToExclude)
                .map(list -> !list.contains(track))
                .orElse(true);
    }

    private Playlist savePlaylist(String name, List<Track> tracks) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setTracks(tracks);
        return playlistRepository.save(playlist);
    }

    private Integer countTrackPlaysOfMonth(YearMonth month, List<TrackHistory> trackHistories) {
        return (int) trackHistories.stream()
                .filter(trackHistory -> isPlayDateInMonth(trackHistory.getPlayDate(), month))
                .count();
    }

    private boolean isPlayDateInMonth(LocalDateTime playDate, YearMonth month) {
        return YearMonth.from(playDate).equals(month);
    }
}
