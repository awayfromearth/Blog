package com.cm.weblog.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/admin/**").authenticated() // 所有以 /admin 开头的接口需要认证
                .anyRequest().permitAll() // 其它接口放行，无需认证
                .and()
                .formLogin() // 使用表单登录
                .and()
                .httpBasic(); // 使用 HTTP Basic 认证
    }
}
