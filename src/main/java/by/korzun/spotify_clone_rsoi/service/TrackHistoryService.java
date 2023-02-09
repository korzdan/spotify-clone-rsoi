package by.korzun.spotify_clone_rsoi.service;

import by.korzun.spotify_clone_rsoi.domain.Playlist;

import java.time.YearMonth;

public interface TrackHistoryService {
    Playlist getPlaylistOfTopTracksOfTheMonth(YearMonth month);
    Playlist getPlaylistOfTopTracksOfTheMonthWithoutCachedTracks(YearMonth month);
    Playlist getRandomTracks(YearMonth month);
}
