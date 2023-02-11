package by.korzun.spotify_spring_console.service;

import by.korzun.spotify_spring_console.domain.Playlist;

import java.time.YearMonth;

public interface TrackHistoryService {
    Playlist getPlaylistOfTopTracksOfTheMonth(YearMonth month);
    Playlist getPlaylistOfTopTracksOfTheMonthWithoutCachedTracks(YearMonth month);
    Playlist getRandomTracks(YearMonth month);
}
