package umc.Jeon.database.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.Jeon.database.location.model.GetUserLocationRes;
import umc.Jeon.database.location.model.Location;
import umc.Jeon.database.location.model.PostUserLocationReq;
import umc.Jeon.database.user.model.User;
import umc.Jeon.repository.UserRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    // 유저의 기본 주소 불러오기
    public GetUserLocationRes getUserLatLng(long userId) {
        String getUserLatLng = "SELECT userId, lat, lng, address FROM Location WHERE userId=? and defaultAddress=true";
        long getUserLatLngParam = userId;

        return this.jdbcTemplate.queryForObject(getUserLatLng,
                (rs, rowNum) -> new GetUserLocationRes(
                        rs.getLong("userId"),
                        rs.getDouble("lat"),
                        rs.getDouble("lng"),
                        rs.getString("address")
                ), getUserLatLngParam);
    }

    // 유저가 저장한 주소 목록 불러오기
    public List<GetUserLocationRes> getUserLocationsRes(long userId) {
        String getUserLocations = "SELECT userId, lat, lng, address FROM Location WHERE userId=? ORDER BY updatedAt DESC";
        long getUserLocationsParam = userId;

        return this.jdbcTemplate.query(getUserLocations,
                (rs, rowNum) -> new GetUserLocationRes(
                        rs.getLong("userId"),
                        rs.getDouble("lat"),
                        rs.getDouble("lng"),
                        rs.getString("address")
                ), getUserLocationsParam);
    }

    // 유저의 주소 추가 하기
    public boolean SetUserLocation(long userId, PostUserLocationReq postUserLocationReq) {
        String getUserLocationExist = "SELECT EXISTS (SELECT * FROM Location WHERE userId=?)";
        long getUserLocationCountParam = userId;

        int exist = this.jdbcTemplate.queryForObject(getUserLocationExist,
                int.class, getUserLocationCountParam);

        if (exist == 0){
            Location location = Location.builder()
                    .userId(userId)
                    .lat(postUserLocationReq.getLat())
                    .lng(postUserLocationReq.getLat())
                    .address(postUserLocationReq.getAddress())
                    .defaultAddress(true)
                    .status(true)
                    .build();
        }
        return false;
    }

    // 유저가 존재하는지 확인
    public boolean checkUserExist(long userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) return true;
        else return false;
    }
}
