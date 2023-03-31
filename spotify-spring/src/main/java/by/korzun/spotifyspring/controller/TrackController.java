package by.korzun.spotifyspring.controller;

import by.korzun.spotifyspring.domain.Track;
import by.korzun.spotifyspring.service.DefaultTrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final DefaultTrackService trackService;

    @GetMapping("/{genre}")
    public List<Track> getTracksByGenre(@PathVariable String genre) {
        return trackService.findAllTracksByGenre(genre);
    }

}
