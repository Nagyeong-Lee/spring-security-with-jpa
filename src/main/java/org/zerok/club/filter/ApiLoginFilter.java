package org.zerok.club.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.zerok.club.dto.ClubAuthMemberDTO;
import org.zerok.club.util.JwtUtil;

import java.io.IOException;

/**
 * api 로그인 처리 및 토큰 발급
 */
@Slf4j
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {
    private JwtUtil jwtUtil;

    public ApiLoginFilter(String defaultFilterProcessesUrl, JwtUtil jwtUtil) {
        super(defaultFilterProcessesUrl);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info(" ### ApiLoginFilter ###");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null) {
            throw new BadCredentialsException("email cannot be null");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    //인증 성공하면 jwt 생성
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info(" ### ApiLoginSuccessHandler ###");
        log.info("principal : {}", authResult.getPrincipal());
        ClubAuthMemberDTO clubAuthMemberDTO = (ClubAuthMemberDTO) authResult.getPrincipal();
        String email = clubAuthMemberDTO.getEmail();
        if (email != null) {
            String jwtToken = jwtUtil.generateToken(email);
            log.info("jwt : {}", jwtToken);
        }
    }
}
