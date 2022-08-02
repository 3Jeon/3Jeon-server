package umc.Jeon.crawling.baemin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.config.exception.BaseResponse;
import umc.Jeon.crawling.User.User;
import umc.Jeon.crawling.baemin.model.*;

import java.util.List;

import static umc.Jeon.crawling.HashValues.HashValues.BAEMIN_CATEGORY;
import static umc.Jeon.crawling.HashValues.HashValues.BAEMIN_SORT;


@RestController
@RequestMapping("/baemin")
public class BaeminController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BaeminService baeminService;

    public BaeminController(BaeminService baeminService){
        this.baeminService = baeminService;
    }

    @ApiOperation(value="배달의민족 주변 매장 조회")
    @ResponseBody
    @GetMapping("/restaurant")
    public BaseResponse<List<BRestaurant>> getBRestaurant(
            @RequestParam String category,
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(required = false, defaultValue = "rank") String sort
    ) {
        // 카테고리, 정렬기준
        category = BAEMIN_CATEGORY.get(category);
        sort = BAEMIN_SORT.get(sort);

        try{
            List<BRestaurant> BRestaurantList = baeminService.getBRestaurant(lat, lng, category, sort);

            return new BaseResponse<>(BRestaurantList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    @ApiOperation(value="배달의민족 메뉴 조회")
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
    @ApiOperation(value = "배달의민족 매장 검색")
    @ResponseBody
    @GetMapping("/search-restaurants")
    public BaseResponse<List<BSRestaurant>> getBSearchRestaurants(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam String search,
            @RequestParam(required = false, defaultValue = "rank") String sort,
            @RequestParam(required = false, defaultValue = "60") int items
    ) {
        // 정렬기준
        sort = BAEMIN_SORT.get(sort);

        try{
            List<BSRestaurant> bsRestaurantList = baeminService.getBSearchRestaurants(lat, lng, search, sort, items);

            return new BaseResponse<>(bsRestaurantList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
