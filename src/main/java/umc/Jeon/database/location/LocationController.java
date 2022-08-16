package umc.Jeon.database.location;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.config.exception.BaseResponse;
import umc.Jeon.database.location.model.Location;
import umc.Jeon.database.location.model.PostUserLocationReq;

import java.util.List;

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
    public BaseResponse<String> setUserLocation(@RequestParam long userId,
                                                 @RequestBody PostUserLocationReq postUserLocationReq){
        try{
            boolean result = locationService.setUserLocation(userId, postUserLocationReq);
            String message;
            if (result)
                message = "주소가 추가 되었습니다.";
            else message = "주소 추가에 실패 하였습니다.";
            return new BaseResponse<>(message);
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

    @ApiOperation(value="유저의 지정된 주소 삭제", notes = "해당 유저의 지정된 주소 status를 false로 변경(삭제 처리)")
    @ResponseBody
    @PatchMapping("/delete")
    public BaseResponse<String> deleteUserLocation(@RequestParam(value = "locationId") long id){
        try{
            locationService.deleteUserLocation(id);
            return new BaseResponse<>("주소를 삭제 하였습니다.");
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ApiOperation(value="유저가 저장한 주소 목록 호출", notes = "해당 유저가 저장한 활성화된 주소 목록 호출")
    @ResponseBody
    @GetMapping("/list")
    public BaseResponse<List<Location>> getUserLocations(@RequestParam long userId){
        try{
            List<Location> locations = locationService.getUserLocations(userId);
            return new BaseResponse<>(locations);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}