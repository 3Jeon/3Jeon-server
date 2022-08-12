package umc.Jeon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.Jeon.database.location.model.Location;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByUserId(long userId);
    Optional<Location> findByUserIdAndDefaultAddress(long userId, boolean defaultAddress);
    Optional<Location> findByUserIdAndStatusAndDefaultAddress(long userId, boolean status, boolean defaultAddress);
    Optional<List<Location>> findByUserIdAndStatusAndDefaultAddressOrderByUpdatedAtDesc(long userId, boolean status, boolean defaultAddress);
}
