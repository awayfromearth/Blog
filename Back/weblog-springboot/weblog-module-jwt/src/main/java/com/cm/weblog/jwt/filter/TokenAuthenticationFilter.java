package com.cm.weblog.jwt.filter;

import com.cm.weblog.jwt.utils.JwtTokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Token 校验过滤器
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private JwtTokenHelper jwtTokenHelper;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.tokenHeaderKey}")
    private String tokenHeaderKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 从请求头获取 key 为 Authorization 的值
        String header = request.getHeader(tokenHeaderKey);

        // 仅校验 admin 开头的请求
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/admin")) {
            // 判断是否以 Bearer 开头
            if (StringUtils.startsWith(header, tokenPrefix)) {
                // 截取 Token
                String token = StringUtils.substring(header, 7);
                log.info("token: {}", token);

                // 判空
                if (StringUtils.isNotBlank(token)) {
                    try {
                        jwtTokenHelper.validateToken(token);
                    } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                        authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 不可用"));
                        return;
                    } catch (ExpiredJwtException e) {
                        authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 已失效"));
                        return;
                    }

                    String username = jwtTokenHelper.getUsernameFromToken(token);

                    if (StringUtils.isNotBlank(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }

        // 继续执行下一个过滤器
        filterChain.doFilter(request, response);
    }
}
