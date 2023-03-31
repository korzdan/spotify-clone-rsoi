package by.korzun.spotifyspring.repository;

import by.korzun.spotifyspring.domain.Track;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends CrudRepository<Track, Long> {
    List<Track> findAll();

    @Query(value = "SELECT * FROM track t WHERE t.is_liked = true LIMIT ?1", nativeQuery = true)
    List<Track> findLikedTracks(Integer numberOfTracks);

    List<Track> findTracksByGenre(String genre);
}
