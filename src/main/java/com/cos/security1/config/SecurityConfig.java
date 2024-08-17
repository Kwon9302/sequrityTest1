package com.cos.security1.config;

import com.cos.security1.config.oauth.CustomAuthenticationEntryPoint;
import com.cos.security1.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    // password를 저장하기위한 password encoding
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                // authorizeHttpRequests : 요청에 대한 권한 부여 규칙을 설정한다.
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").authenticated() // /user는 인증만 되면 들어갈 수 있는 주소.
                        .requestMatchers("/admin/**").hasRole("ADMIN") // //admin으로 들어오면 ADMIN권한이 있는 사람만 들어올 수 있음
                        .anyRequest().permitAll() // 그리고 나머지 url은 전부 권한을 허용해준다.
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint))
                .formLogin(form -> form
                        .loginPage("/login") // "/login" 경로를 로그인페이지로 설정한다. 인증이 필요할 경우 이 페이지로 리디렉션한다.

                        //로그인 폼의 action속성에서 지정된 url을 처리한다.
                        .loginProcessingUrl("/loginProc") //  "/loginProc" 주소가 호출되면 security가 낚아채서 로그인을 해준다.
                        .defaultSuccessUrl("/") // 로그인 성공후 리디렉션할 기본 url주 -> Controller에서 getmapping("/")
                )
                .oauth2Login((oauth2Login -> oauth2Login
                                .loginPage("/login") // google로그인이 된 후에 후처리가 필요함
                                .userInfoEndpoint(userInfo -> userInfo
                                        // 받은 사용자 정보를 처리하는 principalOauth2UserService로 넘겨 정보를 처리한다.
                                        .userService(principalOauth2UserService))
                        )
                );
        System.out.println("SecurityConfig.filterChain");
        return http.build();
    }

}
