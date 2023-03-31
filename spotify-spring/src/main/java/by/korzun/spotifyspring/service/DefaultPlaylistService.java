package by.korzun.spotifyspring.service;

import by.korzun.spotifyspring.domain.Playlist;
import by.korzun.spotifyspring.domain.Track;
import by.korzun.spotifyspring.domain.TrackHistory;
import by.korzun.spotifyspring.domain.dto.TrackPlays;
import by.korzun.spotifyspring.repository.TrackRepository;
import by.korzun.spotifyspring.system_settigns.SystemSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class DefaultPlaylistService implements PlaylistService {

    private final TrackRepository trackRepository;
    private final SystemSettingsService systemSettingsService;

    private static final String TOP_TRACKS_OF_MONTH_PLAYLIST_NAME = "Top tracks of month";
    private static final String TOP_TRACKS_OF_PREVIOUS_MONTH_PLAYLIST_NAME = "Top tracks of previous month";
    private static final String RANDOM_PLAYLIST_NAME = "Random playlist";
    private static final String MY_TOP_LIKED_TRACKS = "My top liked";

    @Override
    public Playlist getPlaylistOfTopTracksOfMonth() {
        return assembleAndSavePlaylistOfTopTracksOfMonth();
    }

    private Playlist assembleAndSavePlaylistOfTopTracksOfMonth() {
        return assemblePlaylistAndRemoveBlocked(TOP_TRACKS_OF_MONTH_PLAYLIST_NAME, getTopTracksOfMonth(YearMonth.now(), null));
    }

    @Override
    public Playlist getPlaylistOfTopTracksOfPreviousMonth() {
        return  assembleAndSavePlaylistOfTopTracksOfPreviousMonth(getPlaylistOfTopTracksOfMonth().getTracks());
    }

    private Playlist assembleAndSavePlaylistOfTopTracksOfPreviousMonth(List<Track> tracksToExclude) {
        return assemblePlaylistAndRemoveBlocked(
                TOP_TRACKS_OF_PREVIOUS_MONTH_PLAYLIST_NAME,
                getTopTracksOfMonth(YearMonth.of(Year.now().getValue(),
                        YearMonth.now().getMonth().getValue() - 1), tracksToExclude)
        );
    }

    @Override
    public Playlist getRandomPlaylist() {
        return assembleAndSaveRandomPlaylist();
    }

    @Override
    public Playlist getMyTopTracks() {
        List<Track> likedTracks = trackRepository.findLikedTracks(systemSettingsService.findNumberOfTracksInPlaylist());
        return assemblePlaylistAndRemoveBlocked(MY_TOP_LIKED_TRACKS, likedTracks);
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
        return assemblePlaylistAndRemoveBlocked(RANDOM_PLAYLIST_NAME, randomTracks);
    }

    private List<Track> getTopTracksOfMonth(YearMonth month, List<Track> tracksToExclude) {
        return trackRepository.
                findAll().stream()
                .map(track -> new TrackPlays(track, countTrackPlaysOfMonth(month, track.getPlayHistory())))
                .sorted(Comparator.comparing(TrackPlays::getTimesPlayed).reversed())
                .map(TrackPlays::getTrack)
                .filter(track -> filterTrack(tracksToExclude, track))
                .limit(systemSettingsService.findNumberOfTracksInPlaylist())
                .toList();
    }

    private boolean filterTrack(List<Track> tracksToExclude, Track track) {
        return Optional.ofNullable(tracksToExclude)
                .map(list -> !list.contains(track))
                .orElse(true);
    }

    private Playlist assemblePlaylistAndRemoveBlocked(String name, List<Track> tracks) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setTracks(filterTracksOnBlocking(tracks));
        return playlist;
    }

    private List<Track> filterTracksOnBlocking(List<Track> tracks) {
        return tracks.stream()
                .filter(track -> !track.getIsBlocked())
                .collect(toList());
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
