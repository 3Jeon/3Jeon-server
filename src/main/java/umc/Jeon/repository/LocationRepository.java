package umc.Jeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.Jeon.config.database.location.model.Location;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByUserId(long userId);
    Optional<Location> findByUserIdAndDefaultAddress(long userId, boolean defaultAddress);
    Optional<List<Location>> findByUserIdAndStatusOrderByUpdatedAtDesc(long userId, boolean status);

}
