package umc.Jeon.crawling.baemin;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.config.exception.BaseResponse;
import umc.Jeon.crawling.user.User;
import umc.Jeon.crawling.baemin.model.*;

import java.util.List;

import static umc.Jeon.crawling.mapping.HashValues.BAEMIN_CATEGORY;
import static umc.Jeon.crawling.mapping.HashValues.BAEMIN_SORT;


@RestController
@RequestMapping("/baemin")
public class BaeminController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BaeminService baeminService;

    public BaeminController(BaeminService baeminService){
        this.baeminService = baeminService;
    }

    @ApiOperation(value = "배달의민족 주변 레스토랑 조회", notes = "카테고리에 기반한 주변 레스토랑 조회")
    @ResponseBody
    @GetMapping("/restaurant")
    public BaseResponse<List<BRestaurant>> getBRestaurant(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam String category,
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
    @ApiOperation(value = "배달의민족 매장 메뉴 조회", notes = "매장 ID로 메뉴 조회")
    @ResponseBody
    @GetMapping("/{restaurant-id}/menu")
    public BaseResponse<BRestaurantInfo> getYMenus(@PathVariable(value = "restaurant-id") int restaurantId){
        double lat = 37.94766676253973;
        double lng = 127.0609096938018;
        try{
            BRestaurantInfo bRestaurantInfo = baeminService.getBRestaurantInfo(restaurantId, lat, lng);

            return new BaseResponse<>(bRestaurantInfo);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @ApiOperation(value = "배달의민족 매장 검색", notes = "키워드를 이용한 매장 검색")
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
