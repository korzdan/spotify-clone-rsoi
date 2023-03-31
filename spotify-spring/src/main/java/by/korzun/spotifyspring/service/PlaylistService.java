package by.korzun.spotifyspring.service;

import by.korzun.spotifyspring.domain.Playlist;

public interface PlaylistService {
    Playlist getPlaylistOfTopTracksOfMonth();
    Playlist getPlaylistOfTopTracksOfPreviousMonth();
    Playlist getRandomPlaylist();
    Playlist getMyTopTracks();
}
