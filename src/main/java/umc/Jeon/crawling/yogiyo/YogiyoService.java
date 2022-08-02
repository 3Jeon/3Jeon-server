package umc.Jeon.crawling.yogiyo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.crawling.yogiyo.component.YogiyoJsoup;
import umc.Jeon.crawling.yogiyo.model.YMenuGroup;
import umc.Jeon.crawling.yogiyo.model.YRestaurant;

import java.util.List;

import static umc.Jeon.config.exception.BaseResponseStatus.*;

@Service
public class YogiyoService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final YogiyoJsoup yogiyoJsoup;

    public YogiyoService(YogiyoJsoup yogiyoJsoup){
        this.yogiyoJsoup = yogiyoJsoup;
    }

    public List<YRestaurant> getYRestaurant(double lat, double lng, String category, String sort, int page, int items) throws BaseException {
        try{
            List<YRestaurant> YRestaurantList = yogiyoJsoup.getYRestaurantList(lat, lng, category, sort, page, items);

            return YRestaurantList;
        } catch(Exception exception){
            throw new BaseException(RESPONSE_ERROR);
        }
    }

    public List<YMenuGroup> getYMenuList(long id) throws BaseException {
        try{
            List<YMenuGroup> YMenuList = yogiyoJsoup.getYMenuList(id);

            return YMenuList;
        } catch (Exception exception){
            throw new BaseException(RESPONSE_ERROR);
        }
    }

    public List<YRestaurant> getYSearchRestaurants(double lat, double lng, String search, String sort, int items, int page) throws BaseException{
        try{
            List<YRestaurant> YRestaurantList = yogiyoJsoup.getYSearchRestaurants(lat, lng, search, sort, items, page);

            return YRestaurantList;
        } catch(Exception exception){
            throw new BaseException(RESPONSE_ERROR);
        }
    }
}
