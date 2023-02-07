package by.korzun.spotify_clone_rsoi.service;

import by.korzun.spotify_clone_rsoi.domain.Playlist;
import by.korzun.spotify_clone_rsoi.domain.TrackHistory;
import by.korzun.spotify_clone_rsoi.util.DateUtils;
import lombok.AllArgsConstructor;

import java.time.YearMonth;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TrackHistoryService {

    private List<TrackHistory> allTrackHistory;

    private static final Integer NUMBER_OF_TOP_TRACKS = 3;

    // TODO: Refactor this method. Returns unsorted tracks.
    public Playlist getPlaylistOfTopTracksOfTheMonth(YearMonth month) {
        Playlist playlist = new Playlist(new HashSet<>());
        allTrackHistory.stream()
                .collect(
                        Collectors.toMap(trackHistory -> trackHistory,
                                trackHistory -> countTrackPlaysInMonth(trackHistory, month))
                ).entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(NUMBER_OF_TOP_TRACKS.longValue())
                .forEach(entry -> playlist.getTracks().add(entry.getKey().getTrack()));
        return playlist;
    }

    private Long countTrackPlaysInMonth(TrackHistory trackHistory ,YearMonth month) {
        return trackHistory.getPlayDates().stream()
                .filter(date -> DateUtils.isDateInMonth(date, month))
                .count();
    }
}
