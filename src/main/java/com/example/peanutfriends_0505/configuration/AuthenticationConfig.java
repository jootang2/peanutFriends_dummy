package com.example.peanutfriends_0505.configuration;

import com.example.peanutfriends_0505.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final MemberService memberService;
    @Value("${jwt.secretKey}")
    public String accessSecretKey;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable() // url인증이 아니고, jwt토큰 인증을 하기 때문에 disable()
                .csrf().disable() //crossSite 관련 : disable()
                .cors().and() // cors 관련 : disable()
                .authorizeRequests() // 여기부터 request 인가 관련
                .requestMatchers(GET, "/api/members/check").permitAll()
                .requestMatchers(POST,"/api/members/signUp", "/api/members/login", "/api/members/refreshToken").permitAll()// 회원가입, 로그인은 항상 허용되어야 한다.
                .requestMatchers(POST,"/api/**").authenticated()// 그 외, 나머지 api는 인증되어야 함
                .requestMatchers(GET,"/api/**").authenticated()// 그 외, 나머지 api는 인증되어야 함
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt를 사용하는 경우 씀
                .and()
                .addFilterBefore(new JwtFilter(memberService, accessSecretKey), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
