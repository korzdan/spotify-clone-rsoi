package by.korzun.spotify_spring_console.service;

import by.korzun.spotify_spring_console.domain.Playlist;
import by.korzun.spotify_spring_console.domain.Track;
import by.korzun.spotify_spring_console.domain.TrackHistory;
import by.korzun.spotify_spring_console.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("defaultTrackHistoryService")
@RequiredArgsConstructor
public class DefaultTrackHistoryService implements TrackHistoryService {

    private final List<TrackHistory> allTrackHistory;
    private static final Integer NUMBER_OF_TOP_TRACKS = 3;

    @Override
    public Playlist getPlaylistOfTopTracksOfTheMonth(YearMonth month) {
        Playlist playlist = new Playlist(new ArrayList<>());
        getStreamOfSortedTracksByPlays(month)
                .limit(NUMBER_OF_TOP_TRACKS.longValue())
                .forEach(entry -> playlist.getTracks().add(entry.getKey().getTrack()));
        return playlist;
    }

    @Override
    public Playlist getPlaylistOfTopTracksOfTheMonthWithoutCachedTracks(YearMonth month) {
        Playlist playlist = new Playlist(new ArrayList<>());
        Playlist oldPlaylist = getPlaylistOfTopTracksOfTheMonth(month);
        getStreamOfSortedTracksByPlays(month.minusMonths(1L))
                .filter(trackEntry -> !oldPlaylist.getTracks().contains(trackEntry.getKey().getTrack()))
                .limit(NUMBER_OF_TOP_TRACKS.longValue())
                .forEach(entry -> playlist.getTracks().add(entry.getKey().getTrack()));
        return playlist;
    }

    @Override
    public Playlist getRandomTracks(YearMonth month) {
        Playlist playlist = new Playlist(new ArrayList<>());
        Track trackFromTheFirstPlaylist = getPlaylistOfTopTracksOfTheMonth(month).getTracks().get(0);
        Track trackFromTheSecondPlaylist = getPlaylistOfTopTracksOfTheMonthWithoutCachedTracks(month).getTracks().get(0);
        playlist.getTracks().add(trackFromTheFirstPlaylist);
        playlist.getTracks().add(trackFromTheSecondPlaylist);
        getStreamOfSortedTracksByPlays(month)
                .filter(entry -> !playlist.getTracks().contains(entry.getKey().getTrack()))
                .limit(3L)
                .forEach(entry -> playlist.getTracks().add(entry.getKey().getTrack()));
        return playlist;
    }

    private Stream<Map.Entry<TrackHistory, Long>> getStreamOfSortedTracksByPlays(YearMonth month) {
        return allTrackHistory.stream()
                .collect(
                        Collectors.toMap(trackHistory -> trackHistory,
                                trackHistory -> countTrackPlaysInMonth(trackHistory, month))
                ).entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
    }

    private Long countTrackPlaysInMonth(TrackHistory trackHistory ,YearMonth month) {
        return trackHistory.getPlayDates().stream()
                .filter(date -> DateUtils.isDateInMonth(date, month))
                .count();
    }
}
