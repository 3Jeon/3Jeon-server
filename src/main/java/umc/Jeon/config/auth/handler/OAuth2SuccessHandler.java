package umc.Jeon.config.auth.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import umc.Jeon.config.auth.dto.SessionUser;
import umc.Jeon.domain.user.User;
import umc.Jeon.repository.UserRepository;
import umc.Jeon.config.auth.jwt.JwtService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();
        SessionUser sessionMember = (SessionUser) session.getAttribute("user");
        Optional<User> user = userRepository.findByEmail(sessionMember.getEmail());

        String jwt = jwtService.createJwt(user.get().getId());
//        System.out.println(user.get().getId()+"??????????????????????????????????????????????");
//        System.out.println(sessionMember.getId()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println(jwt);

        String targetUrl = UriComponentsBuilder.fromUriString("/auth")
                .queryParam("jwt",jwt)
                .queryParam("id", user.get().getId())
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request,response,targetUrl);

    }

}
