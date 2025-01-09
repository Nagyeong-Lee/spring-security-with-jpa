package org.zerok.club.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.AntPathMatcher;
import org.zerok.club.filter.ApiCheckFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter("/notes/**/*", new AntPathMatcher()); // /notes/**/* 경로로 요청이 들어올때만 ApiCheckFilter 동작하도록
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /*.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/sample/all").permitAll()
                                .requestMatchers("/sample/member").hasRole("USER")
                                .requestMatchers("/sample/admin").hasRole("ADMIN")
                                .anyRequest().authenticated())
                @PreAuthorize로 대체
                */

                .csrf(csrf -> csrf.disable())
                .formLogin(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())
                .rememberMe(r -> r.tokenValiditySeconds(60*60*24*7)) //자동로그인 (formLogin일때만 가능, remeber-me라는 쿠키 생성)
        ;

        return http.build();
    }
}
