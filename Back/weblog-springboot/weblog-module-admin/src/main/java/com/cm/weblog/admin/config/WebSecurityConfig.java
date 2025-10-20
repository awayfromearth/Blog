package com.cm.weblog.admin.config;

import com.cm.weblog.jwt.config.JwtAuthenticationSecurityConfig;
import com.cm.weblog.jwt.filter.TokenAuthenticationFilter;
import com.cm.weblog.jwt.handler.RestAccessDeniedHandler;
import com.cm.weblog.jwt.handler.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;

    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Resource
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 禁用 csrf
                .formLogin().disable()// 禁用表单登录
                .apply(jwtAuthenticationSecurityConfig) // 设置用户登录认证相关配置
              .and()
                .authorizeRequests()
                .mvcMatchers("/admin/**").authenticated() // 所有以 /admin 开头的接口需要认证
                .anyRequest().permitAll() // 其它接口放行，无需认证
              .and()
                .httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint) // 处理用户未登录
              .and()
                .exceptionHandling().accessDeniedHandler(restAccessDeniedHandler) // 处理用户权限不够
              .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 前后端分离，无需创建会话
              .and()
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // 将 Token 校验放在用户认证之前
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }
}
