package umc.Jeon.crawling.coupang;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.config.exception.BaseResponse;
import umc.Jeon.crawling.user.User;
import umc.Jeon.crawling.coupang.model.*;

import java.util.List;

import static umc.Jeon.crawling.mapping.HashValues.*;


@RestController
@RequestMapping("/coupang")
public class CoupangController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CoupangService coupangService;

    public CoupangController(CoupangService coupangService) {
        this.coupangService = coupangService;
    }

    @ApiOperation(value = "쿠팡이츠 주변 레스토랑 조회", notes = "카테고리에 기반한 주변 레스토랑 조회")
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

    @ApiOperation(value = "쿠팡이츠 매장 메뉴 조회", notes = "매장 ID로 메뉴 조회")
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

    @ApiOperation(value = "쿠팡이츠 매장 검색", notes = "키워드를 이용한 매장 검색")
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
