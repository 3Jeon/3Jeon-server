package umc.Jeon.database.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.Jeon.database.location.model.Location;
import umc.Jeon.database.location.model.PostUserLocationReq;
import umc.Jeon.database.user.UserDao;
import umc.Jeon.repository.LocationRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class LocationDao {
    private final LocationRepository locationRepository;
    private final UserDao userDao;

    // 유저의 기본 주소 불러오기
    public Location getUserDefaultLocation(long userId) {
        Optional<Location> defaultLocation = locationRepository.findByUserIdAndDefaultAddress(userId, true);
        return defaultLocation.orElse(null);
    }

    // 유저가 저장한 주소 목록 불러오기
    public List<Location> getUserLocationsRes(long userId) {
        Optional<List<Location>> locations = locationRepository.findByUserIdAndStatusOrderByUpdatedAtDesc(userId, true);
        return locations.orElse(null);
    }

    // 유저의 주소 추가 하기
    public boolean setUserLocation(long userId, PostUserLocationReq postUserLocationReq) {
        if (!userDao.checkUserExist(userId)) // 유저가 존재하는지 확인
            return false;

        boolean exist = locationRepository.existsByUserId(userId);

        Location location;
        if (exist){ // 데이터 존재 O
            location = Location.builder()
                    .userId(userId)
                    .lat(postUserLocationReq.getLat())
                    .lng(postUserLocationReq.getLat())
                    .address(postUserLocationReq.getAddress())
                    .defaultAddress(false)
                    .status(true)
                    .build();
        } else  { // 데이터 존재 X
            location = Location.builder()
                    .userId(userId)
                    .lat(postUserLocationReq.getLat())
                    .lng(postUserLocationReq.getLng())
                    .address(postUserLocationReq.getAddress())
                    .defaultAddress(true)
                    .status(true)
                    .build();
        }
        locationRepository.save(location);
        return true;
    }

    // 새로운 주소 추가후 기본 주소로 변경 하기
    public Location changeUserDefaultLocation(long userId, PostUserLocationReq postUserLocationReq){
        Location prevLocation = getUserDefaultLocation(userId);
        if (prevLocation != null) {
            prevLocation.setDefaultAddress(false);
            locationRepository.save(prevLocation);
        }

        Location newDefaultLocation = Location.builder()
                .userId(userId)
                .lat(postUserLocationReq.getLat())
                .lng(postUserLocationReq.getLng())
                .address(postUserLocationReq.getAddress())
                .defaultAddress(true)
                .status(true)
                .build();
        locationRepository.save(newDefaultLocation);

        return newDefaultLocation;
    }
    // 저장된 주소 삭제 하기
    public void deleteUserLocation(long id){
        Optional<Location> delLocation = locationRepository.findById(id);
        Location location = delLocation.orElse(null);
        location.setStatus(false);
    }
}
