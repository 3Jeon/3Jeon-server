package umc.Jeon.naver_map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.database.location.LocationDao;
import umc.Jeon.database.location.model.Location;
import umc.Jeon.naver_map.component.NaverGeocode;
import umc.Jeon.naver_map.component.NaverMapJsoup;
import umc.Jeon.naver_map.model.GeoStats;
import umc.Jeon.naver_map.model.MapStats;

import static umc.Jeon.config.exception.BaseResponseStatus.*;

@Service
public class NaverMapService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final NaverMapJsoup naverMapJsoup;
    private final NaverGeocode naverGeocode;
    private final LocationDao locationDao;

    public NaverMapService(NaverMapJsoup naverMapJsoup, NaverGeocode naverGeocode, LocationDao locationDao) {
        this.naverMapJsoup = naverMapJsoup;
        this.naverGeocode = naverGeocode;
        this.locationDao = locationDao;
    }

    public MapStats getMapStats(String start, String goal) throws BaseException {
        try {
            MapStats mapStats = naverMapJsoup.getNaverMapStats(start, goal);
            return mapStats;
        } catch (Exception exception) {
            throw new BaseException(REQUEST_ERROR);
        }
    }

    public GeoStats getNaverGeocode(long userId, String query) throws BaseException{
        Location userLocation = locationDao.getUserDefaultLocation(userId);
        if (userLocation == null) {
            userLocation = new Location(); // 기본 검색 주소로 네이버 주소 사용
            userLocation.setLat(37.3595669);
            userLocation.setLng(127.1054065);
        }
        String coordinate = String.format("%s, %s", String.valueOf(userLocation.getLng()), String.valueOf(userLocation.getLat()));
        try{
            GeoStats geoStats = naverGeocode.getNaverGeocode(userId, query, coordinate);
            return geoStats;
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_LOAD_GEO);
        }
    }

}
