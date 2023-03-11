package by.korzun.spotifyspring.controller;

import by.korzun.spotifyspring.domain.Playlist;
import by.korzun.spotifyspring.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/top")
    private ResponseEntity<Playlist> getTopTracksOfMonth() {
        return ResponseEntity.ok(playlistService.getPlaylistOfTopTracksOfMonth());
    }

    @GetMapping("/top-previous")
    private ResponseEntity<Playlist> getTopTracksOfPreviousMonth() {
        return ResponseEntity.ok(playlistService.getPlaylistOfTopTracksOfPreviousMonth());
    }

    @GetMapping("/random")
    private ResponseEntity<Playlist> getRandomPlaylist() {
        return ResponseEntity.ok(playlistService.getRandomPlaylist());
    }

}
