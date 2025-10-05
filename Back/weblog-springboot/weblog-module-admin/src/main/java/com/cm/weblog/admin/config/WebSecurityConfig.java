package com.cm.weblog.admin.config;

import com.cm.weblog.jwt.config.JwtAuthenticationSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.annotation.Resource;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 禁用 csrf
                .formLogin().disable()// 禁用表单登录
                .apply(jwtAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()
                .mvcMatchers("/admin/**").authenticated() // 所有以 /admin 开头的接口需要认证
                .anyRequest().permitAll() // 其它接口放行，无需认证
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
