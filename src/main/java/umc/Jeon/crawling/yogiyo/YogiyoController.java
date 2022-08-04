package umc.Jeon.crawling.yogiyo;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.config.exception.BaseResponse;
import umc.Jeon.crawling.yogiyo.model.*;

import java.util.List;

import static umc.Jeon.crawling.HashValues.HashValues.YOGIYO_CATEGORY;
import static umc.Jeon.crawling.HashValues.HashValues.YOGIYO_SORT;


@RestController
@RequestMapping("/yogiyo")
public class YogiyoController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final YogiyoService yogiyoService;

    public YogiyoController(YogiyoService yogiyoService){
        this.yogiyoService = yogiyoService;
    }

    @ApiOperation(value = "요기요 주변 레스토랑 조회", notes = "카테고리에 기반한 주변 레스토랑 조회")
    @ResponseBody
    @GetMapping("/restaurant")
    public BaseResponse<List<YRestaurant>> getYRestaurant(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam String category,
            @RequestParam(required = false, defaultValue = "rank") String sort,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "50") int items
    ) {
        category = YOGIYO_CATEGORY.get(category);
        sort = YOGIYO_SORT.get(sort);
        try{
            List<YRestaurant> YRestaurantList = yogiyoService.getYRestaurant(lat, lng, category, sort, page, items);

            return new BaseResponse<>(YRestaurantList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ApiOperation(value = "요기요 매장 메뉴 조회", notes = "매장 ID로 메뉴 조회")
    @ResponseBody
    @GetMapping("/{restaurant-id}/menu")
    public BaseResponse<List<YMenuGroup>> getYMenus(@PathVariable(value = "restaurant-id") long restaurantId){
        try{
            List<YMenuGroup> yMenuList = yogiyoService.getYMenuList(restaurantId);

            return new BaseResponse<>(yMenuList);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "요기요 매장 검색", notes = "키워드를 이용한 매장 검색")
    @ResponseBody
    @GetMapping("/search-restaurants")
    public BaseResponse<List<YRestaurant>> getYSearchRestaurants(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam String search,
            @RequestParam(required = false, defaultValue = "rank") String sort,
            @RequestParam(required = false, defaultValue = "60") int items,
            @RequestParam(required = false, defaultValue = "0") int page
    ) {
        sort = YOGIYO_SORT.get(sort);

        try{
            List<YRestaurant> YRestaurantList = yogiyoService.getYSearchRestaurants(lat, lng, search, sort, items, page);

            return new BaseResponse<>(YRestaurantList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
