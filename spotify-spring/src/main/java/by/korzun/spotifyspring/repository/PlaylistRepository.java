package by.korzun.spotifyspring.repository;

import by.korzun.spotifyspring.domain.Playlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
    Playlist getPlaylistByName(String name);
}
