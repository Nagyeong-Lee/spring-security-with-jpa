package org.zerok.club.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
//request로 들어온 토큰 검사 필터
public class ApiCheckFilter extends OncePerRequestFilter {
    private final String pattern;
    private final AntPathMatcher antPathMatcher;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(antPathMatcher.match(pattern, request.getRequestURI())) {
            log.info("### API CHECK FILTER ###");
            return;
        }
        filterChain.doFilter(request, response); //다음 필터로 넘어가기
    }
}
