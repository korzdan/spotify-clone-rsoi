package by.korzun.spotifyspring.system_settigns;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemSettingsRepository extends CrudRepository<SystemSettings, Long> {
    SystemSettings findByName(String name);
}
