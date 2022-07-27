package umc.Jeon.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOauth2UserService;
    private final Oauth2SuccessHandler successHandler;

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
                    .oauth2Login()
                        .successHandler(successHandler)
                        .userInfoEndpoint()
                            .userService(customOauth2UserService);
    }
}
