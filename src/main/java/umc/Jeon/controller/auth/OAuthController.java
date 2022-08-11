package umc.Jeon.controller.auth;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.Jeon.config.auth.service.CustomOAuth2UserService;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.config.exception.BaseResponse;
import umc.Jeon.config.auth.dto.OAuthResponse;

@RequiredArgsConstructor
@RestController
public class OAuthController {
    private final CustomOAuth2UserService userService;

    @ApiOperation(value = "로그인 완료후 리다이렉트")
    @GetMapping("/auth")
    public BaseResponse<OAuthResponse> jwtResponse(@RequestParam String jwt, @RequestParam Long id) {

        return new BaseResponse<>(new OAuthResponse(id,jwt));
    }

    @DeleteMapping("/withdraw")
    public BaseResponse<Long> deleteUser() throws BaseException {
        return new BaseResponse<Long>(userService.deleteUser());

    }
}
