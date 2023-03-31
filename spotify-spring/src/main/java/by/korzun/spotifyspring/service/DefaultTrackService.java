package by.korzun.spotifyspring.service;

import by.korzun.spotifyspring.domain.Track;
import by.korzun.spotifyspring.domain.TrackGenre;
import by.korzun.spotifyspring.exception.NotGenreFound;
import by.korzun.spotifyspring.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTrackService implements TrackService {

    private final TrackRepository trackRepository;

    @Override
    public List<Track> findAllTracksByGenre(String genre) {
        return trackRepository.findTracksByGenre(validateTrackGenre(genre.toUpperCase()));
    }

    private String validateTrackGenre(String genre) {
        try {
            return TrackGenre.valueOf(genre.toUpperCase()).toString();
        } catch (IllegalArgumentException e) {
            throw new NotGenreFound("Genre " + genre + " is not supported yet.");
        }
    }
}
