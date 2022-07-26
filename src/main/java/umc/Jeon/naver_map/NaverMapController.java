package umc.Jeon.naver_map;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.config.exception.BaseResponse;
import umc.Jeon.naver_map.component.NaverGeocode;
import umc.Jeon.naver_map.model.GeoStats;
import umc.Jeon.naver_map.model.MapStats;

import static umc.Jeon.config.exception.BaseResponseStatus.*;


@RestController
@RequestMapping("/map")
public class NaverMapController {

    final NaverMapService naverMapService;

    public NaverMapController(NaverMapService naverMapService) {
        this.naverMapService = naverMapService;
    }

    @ApiOperation(value = "네이버 경로 API", notes = "키워드를 이용한 매장 검색")
    @ResponseBody
    @GetMapping("/direction")
    public BaseResponse<MapStats> getMapStats(
            @RequestParam(value = "start-lat") String startLat,
            @RequestParam(value = "start-lng") String startLng,
            @RequestParam(value = "goal-lat") String goalLat,
            @RequestParam(value = "goal-lng") String goalLng
    ) {
        // (경도, 위도) -> "127.1058342,37.359708", "129.075986,35.179470"
        String start = String.format("%s, %s", startLng, startLat); // 시작점
        String goal = String.format("%s, %s", goalLng, goalLat); // 도착점

        try {
            MapStats mapStats = naverMapService.getMapStats(start, goal);

            return new BaseResponse<>(mapStats);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ApiOperation(value = "네이버 주소 <-> 좌표 변환", notes = "주소를 좌표로 변환해주는 API")
    @ResponseBody
    @GetMapping("/geocode")
    public BaseResponse<GeoStats> getGeoStats(
            @RequestParam(value = "userId", defaultValue = "0") long userId,
            @RequestParam(value = "location") String query
            ){
        try {
            GeoStats geoStats = naverMapService.getNaverGeocode(userId, query);
            return new BaseResponse<>(geoStats);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}