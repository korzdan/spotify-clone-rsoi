package by.korzun.spotify_clone_rsoi.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TrackHistory {
    private Track track;
    private List<LocalDateTime> playDates;
}