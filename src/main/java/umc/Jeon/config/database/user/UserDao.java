package umc.Jeon.config.database.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.Jeon.config.database.user.model.GetUserLocationRes;
import umc.Jeon.config.database.user.model.Location;
import umc.Jeon.config.database.user.model.PostUserLocationReq;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;

@Repository
@PersistenceContext
public class UserDao {
    private JdbcTemplate jdbcTemplate;
    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

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
    }
}
