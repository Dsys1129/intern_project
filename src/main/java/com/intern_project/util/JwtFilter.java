package com.intern_project.util;

import com.intern_project.user.domain.UserInfo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {
                "/api/user/login",
                "/api/user/register",
                "/swagger-ui/api-docs",
                "/v3/api-docs",
                "/swagger-ui",
                "/swagger-ui.html",
                "/api-docs"
        };
        String path = request.getRequestURI();
        log.debug("Request URI: " + path);
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                Claims claims = jwtUtil.getClaimsFromToken(token);
                if (claims != null && !jwtUtil.isTokenExpired(token)) {
                    // 요청 속성에 클레임을 설정하여 애플리케이션 내에서 사용할 수 있도록 합니다.
                    Long groupId = claims.get("groupId", Long.class);
                    Long userId = claims.get("userId", Long.class);
                    UserInfo userinfo = new UserInfo(groupId, userId);

                    if (jwtUtil.validateToken(token,groupId,userId)) {
                        log.debug("JwtFilter: Valid token, setting claims in request");
                        //userinfo를 request에 set
                        request.setAttribute("userinfo" , userinfo);
                    } else {
                        log.debug("JwtFilter: Invalid token");
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                        return;
                    }
                } else {
                    log.debug("JwtFilter: Token is expired");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is expired");
                    return;
                }
            } catch (Exception e) {

                log.debug("JwtFilter: Invalid token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        } else {
            log.debug("JwtFilter: Missing or invalid Authorization header");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header missing or does not contain Bearer token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
