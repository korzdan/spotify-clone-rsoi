package by.korzun.spotifyspring.domain.dto;

import by.korzun.spotifyspring.domain.Track;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackPlays {
    private Track track;
    private Integer timesPlayed;
}
