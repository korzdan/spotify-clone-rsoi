package by.korzun.spotifyspring.repository;

import by.korzun.spotifyspring.domain.Track;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends CrudRepository<Track, Long> {
    List<Track> findAll();
}
