package umc.Jeon.database.location;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.config.exception.BaseResponse;
import umc.Jeon.database.location.model.Location;
import umc.Jeon.database.location.model.PostUserLocationReq;

@Controller
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController {
    final LocationService locationService;

    @ApiOperation(value="유저의 기본 주소 불러오기", notes = "유저 ID를 이용하여 기본 주소 검색")
    @ResponseBody
    @GetMapping("/default")
    public BaseResponse<Location> getUserDefaultLocation(@RequestParam long userId) {
        try {
            Location defaultLocation = locationService.getUserDefaultLocation(userId);
            return new BaseResponse<>(defaultLocation);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    @ApiOperation(value="유저의 주소 추가",
            notes = "유저의 주소 정보를 이용하여 주소 추가(저장된 주소가 없으면 기본 주소로 설정")
    @ResponseBody
    @PostMapping("/add")
    public BaseResponse<Boolean> setUserLocation(@RequestParam long userId,
                                                 @RequestBody PostUserLocationReq postUserLocationReq){
        try{
            boolean result = locationService.setUserLocation(userId, postUserLocationReq);
            return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ApiOperation(value="유저의 기본 주소 변경", notes = "새로 들어온 주소를 기본 주소로 설정하고 원래의 기본 주소는 일반 주소로 변경")
    @ResponseBody
    @PostMapping("/change/default")
    public BaseResponse<Location> changeUserDefaultLocation(@RequestParam long userId,
                                                            @RequestBody PostUserLocationReq postUserLocationReq){
        try{
            Location newDefaultLocation = locationService.changeUserDefaultLocation(userId, postUserLocationReq);
            return new BaseResponse<>(newDefaultLocation);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
