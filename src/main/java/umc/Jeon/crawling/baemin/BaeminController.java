package umc.Jeon.crawling.baemin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.config.exception.BaseResponse;
import umc.Jeon.crawling.User.User;
import umc.Jeon.crawling.baemin.model.*;

import java.util.List;


@RestController
@RequestMapping("/baemin")
public class BaeminController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BaeminService baeminService;

    public BaeminController(BaeminService baeminService){
        this.baeminService = baeminService;
    }

    @ResponseBody
    @GetMapping("/restaurant")
    public BaseResponse<List<BRestaurant>> getBRestaurant(
            @RequestParam(required = false, defaultValue = "all") String category,
            @RequestParam double lat,
            @RequestParam double lng
    ) {
        try{
            List<BRestaurant> BRestaurantList = baeminService.getBRestaurant(category, lat, lng);

            return new BaseResponse<>(BRestaurantList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/{restaurant-id}/menu")
    public BaseResponse<BRestaurantInfo> getYMenus(@PathVariable(value = "restaurant-id") int restaurantId){
        try{
            // Test Data
            User user= new User();
            user.setLat(37.54766676253973);
            user.setLng(127.0609096938018);

            BRestaurantInfo bRestaurantInfo = baeminService.getBRestaurantInfo(restaurantId, user.getLat(), user.getLng());

            return new BaseResponse<>(bRestaurantInfo);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/search-restaurants")
    public BaseResponse<List<BSRestaurant>> getBSearchRestaurants(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam String search,
            @RequestParam(required = false, defaultValue = "60") int items
    ) {
        try{
            List<BSRestaurant> bsRestaurantList = baeminService.getBSearchRestaurants(lat, lng, search, items);

            return new BaseResponse<>(bsRestaurantList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
