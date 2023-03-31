package by.korzun.spotifyspring.controller;

import by.korzun.spotifyspring.system_settigns.SystemSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemSettingsController {

    private final SystemSettingsService systemSettingsService;

    @PostMapping("/numberOfTracksInPlaylist/{value}")
    public ResponseEntity<Object> updateNumberOfTracksInPlaylist(@PathVariable String value) {
        systemSettingsService.setNumberOfTracksInPlaylist(value);
        return new ResponseEntity<>(OK);
    }
}
