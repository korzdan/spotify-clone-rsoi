package by.korzun.spotify_clone_rsoi.service;

import by.korzun.spotify_clone_rsoi.domain.Playlist;
import by.korzun.spotify_clone_rsoi.domain.TrackHistory;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TrackHistoryService {

    private List<TrackHistory> allTrackHistory;

    private static final Integer NUMBER_OF_TOP_TRACKS = 3;

    // TODO: Test this method
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
                .filter(date -> isDateInMonth(date, month))
                .count();
    }

    private boolean isDateInMonth(LocalDateTime date, YearMonth month) {
        return date.isAfter(month.atDay(1).atStartOfDay()) &&
                date.isBefore(month.plusMonths(1).atDay(1).atStartOfDay());
    }
}
