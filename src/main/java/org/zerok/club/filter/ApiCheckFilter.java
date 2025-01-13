package org.zerok.club.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerok.club.util.JwtUtil;

import java.io.IOException;

/**
 * 특정 URL 호출 시 전달된 토큰 검사하는 필터
 */
@Slf4j
@RequiredArgsConstructor
public class ApiCheckFilter extends OncePerRequestFilter {
    private final AntPathMatcher antPathMatcher;
    private final String pattern;
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(antPathMatcher.match(pattern, request.getRequestURI()))  {
            log.info(" ### ApiCheckFilter ###");
            log.info(" ### uri : {} ###", request.getRequestURI());

            boolean isAuthorizationValid = checkAuthorization(request);
            if(!isAuthorizationValid) {
                return;
            }
        }
        filterChain.doFilter(request, response); //다음 필터로 넘어가기
    }

    private boolean checkAuthorization(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String email = jwtUtil.getEmailByJwtToken(authHeader.split(" ")[1]);
            if (email != null) return true;
        }
        return false;
    }
}
