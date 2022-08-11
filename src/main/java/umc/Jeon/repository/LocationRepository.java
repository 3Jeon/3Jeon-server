package umc.Jeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.Jeon.config.database.user.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
