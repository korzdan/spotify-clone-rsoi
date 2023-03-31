package by.korzun.spotifyspring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "track")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String name;
    private String genre;
    private String description;
    private Boolean isBlocked;
    private Boolean isLiked;
    private String imageUrl;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "track")
    @JsonIgnore
    private List<TrackHistory> playHistory;
    @ManyToMany(mappedBy = "tracks", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Playlist> playlists;
}
