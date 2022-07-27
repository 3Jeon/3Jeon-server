package umc.Jeon.controller.auth;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.Jeon.config.exception.BaseResponse;
import umc.Jeon.domain.auth.dto.OAuthResponse;

@RequiredArgsConstructor
@RestController
public class OAuthController {

    @ApiOperation(value = "로그인 완료후 리다이렉트")
    @GetMapping("/auth")
    public BaseResponse<OAuthResponse> jwtResponse(@RequestParam String jwt, @RequestParam Long id) {
        return new BaseResponse<>(new OAuthResponse(id,jwt));
    }

}
