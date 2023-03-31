package by.korzun.spotifyspring.service;

import by.korzun.spotifyspring.domain.Track;

import java.util.List;

public interface TrackService {
    List<Track> findAllTracksByGenre(String genre);
}
