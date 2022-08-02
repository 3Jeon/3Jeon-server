package umc.Jeon.crawling.coupang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.config.exception.BaseResponse;
import umc.Jeon.crawling.User.User;
import umc.Jeon.crawling.coupang.model.*;

import java.util.List;

import static umc.Jeon.crawling.HashValues.HashValues.*;


@RestController
@RequestMapping("/coupang")
public class CoupangController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CoupangService coupangService;

    public CoupangController(CoupangService coupangService) {
        this.coupangService = coupangService;
    }

    @ResponseBody
    @GetMapping("/restaurant")
    public BaseResponse<List<CRestaurant>> getCRestaurant(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam String category,
            @RequestParam(required = false, defaultValue = "rank") String sort
    ) {
        category = COUPANG_CATEGORY.get(category);
        sort = COUPANG_SORT.get(sort);
        try{
            List<CRestaurant> cRestaurantList = coupangService.getCRestaurant(lat, lng, category, sort);

            return new BaseResponse<>(cRestaurantList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/{restaurant-id}/menu")
    public BaseResponse<CRestaurantMenus> getCMenus(@PathVariable(value = "restaurant-id") int restaurantId){
        try{
            // Test Data
            User user= new User();
            user.setLat(37.54766676253973);
            user.setLng(127.0609096938018);

            CRestaurantMenus cRestaurantMenus = coupangService.getCRestaurantMenus(restaurantId, user.getLat(), user.getLng());

            return new BaseResponse<>(cRestaurantMenus);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/search-restaurants")
    public BaseResponse<List<CRestaurant>> getCSearchRestaurants(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam String search,
            @RequestParam(required = false, defaultValue = "rank") String sort
    ) {
        sort = BAEMIN_SORT.get(sort);

        try{
            List<CRestaurant> bsRestaurantList = coupangService.getCSearchRestaurants(lat, lng, search, sort);

            return new BaseResponse<>(bsRestaurantList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
