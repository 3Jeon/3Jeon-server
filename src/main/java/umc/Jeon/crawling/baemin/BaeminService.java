package umc.Jeon.crawling.baemin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.crawling.baemin.component.BaeminJsoup;
import umc.Jeon.crawling.baemin.model.*;

import java.util.List;

import static umc.Jeon.config.exception.BaseResponseStatus.*;


@Service
public class BaeminService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BaeminJsoup baeminJsoup;

    public BaeminService(BaeminJsoup baeminJsoup){
        this.baeminJsoup = baeminJsoup;
    }

    public List<BRestaurant> getBRestaurant(String category, double lat, double lng) throws BaseException {
        try{
            List<BRestaurant> BRestaurantList = baeminJsoup.getBRestaurant(category, lat, lng);

            return BRestaurantList;
        } catch(Exception exception){
            throw new BaseException(RESPONSE_ERROR);
        }
    }

    public BRestaurantInfo getBRestaurantInfo(long id, double lat, double lng) throws BaseException {
        try{
            BRestaurantInfo bRestaurantInfo = baeminJsoup.getBRestaurantInfo(id, lat, lng);

            return bRestaurantInfo;
        } catch (Exception exception){
            throw new BaseException(RESPONSE_ERROR);
        }
    }

    public List<BSRestaurant> getBSearchRestaurants(double lat, double lng, String search, int items) throws BaseException{
        try{
            List<BSRestaurant> bsRestaurantList = baeminJsoup.getBSearchRestaurants(lat, lng, search, items);

            return bsRestaurantList;
        } catch(Exception exception){
            throw new BaseException(RESPONSE_ERROR);
        }
    }
}