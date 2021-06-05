package ntiteam.test.planetOverlord.repository;

import ntiteam.test.planetOverlord.models.Overlord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OverlordRepo extends CrudRepository<Overlord, Long> {
    @Query(value = "select over from Overlord over where over.planets is empty")
    List<Overlord> findSlackerOverlords();

    @Query(value = "select * from overlords order by overlord_age asc limit 10", nativeQuery = true)
    List<Overlord> findTopOfYoungOverlords();
}
