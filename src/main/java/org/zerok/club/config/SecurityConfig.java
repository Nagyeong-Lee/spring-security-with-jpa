package org.zerok.club.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.zerok.club.filter.ApiCheckFilter;
import org.zerok.club.filter.ApiLoginFilter;
import org.zerok.club.handler.ApiLoginFaliHandler;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter(new AntPathMatcher(), "/notes/**/*");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //AbstractAuthenticationProcessingFilter는 AuthenticationManager 필수
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        // /api/login 호출 시 ApiLoginFilter 동작하도록 설정
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login");
        apiLoginFilter.setAuthenticationManager(authenticationManager);
        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFaliHandler());

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

                /* ApiCheckFilter */
                .addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class)

                /* ApiLoginFilter */
                .authenticationManager(authenticationManager)
                .addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }
}
