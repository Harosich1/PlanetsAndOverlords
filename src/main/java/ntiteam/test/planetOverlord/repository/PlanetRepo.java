package ntiteam.test.planetOverlord.repository;

import ntiteam.test.planetOverlord.models.Planet;
import org.springframework.data.repository.CrudRepository;

public interface PlanetRepo extends CrudRepository<Planet, Long> {
}
