package umc.Jeon.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import umc.Jeon.config.auth.handler.OAuth2SuccessHandler;
import umc.Jeon.config.auth.service.CustomOAuth2UserService;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOauth2UserService;
    private final OAuth2SuccessHandler successHandler;
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    /** 로그인 페이지 생성 **/
                    .oauth2Login()
                        .successHandler(successHandler)
                        /** 네이버 USER INFO의 응답을 처리하기 위한 설정 **/
                        .userInfoEndpoint()
                            .userService(customOauth2UserService);
    }
}
