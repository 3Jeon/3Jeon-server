package umc.Jeon.crawling.coupang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.crawling.coupang.component.CoupangJsoup;
import umc.Jeon.crawling.coupang.model.*;

import java.util.List;

import static umc.Jeon.config.exception.BaseResponseStatus.RESPONSE_ERROR;


@Service
public class CoupangService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CoupangJsoup coupangJsoup;

    public CoupangService(CoupangJsoup coupangJsoup){
        this.coupangJsoup = coupangJsoup;
    }

    public List<CRestaurant> getCRestaurant(double lat, double lng, String category, String sort) throws BaseException {
        try{
            List<CRestaurant> cRestaurantList = coupangJsoup.getCRestaurants(lat, lng, category, sort);

            return cRestaurantList;
        } catch(Exception exception){
            throw new BaseException(RESPONSE_ERROR);
        }
    }

    public CRestaurantMenus getCRestaurantMenus(long id, double lat, double lng) throws BaseException {
        try{
            CRestaurantMenus cRestaurantMenus = coupangJsoup.getCRestaurantMenus(id, lat, lng);

            return cRestaurantMenus;
        } catch (Exception exception){
            throw new BaseException(RESPONSE_ERROR);
        }
    }

    public List<CRestaurant> getCSearchRestaurants(double lat, double lng, String search, String sort) throws BaseException{
        try{
            List<CRestaurant> cRestaurantList = coupangJsoup.getCSearchRestaurants(lat, lng, search, sort);

            return cRestaurantList;
        } catch(Exception exception){
            throw new BaseException(RESPONSE_ERROR);
        }
    }
}
