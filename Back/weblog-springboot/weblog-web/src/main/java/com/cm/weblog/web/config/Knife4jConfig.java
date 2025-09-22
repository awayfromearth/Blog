package com.cm.weblog.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
@Profile("dev")
public class Knife4jConfig {
    @Bean("webApi")
    public Docket createApiDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .groupName("Web 前台接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cm.weblog.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("Weblog 博客前台接口文档")
                .description("Weblog 是一款由 SpringBoot + Vue 开发的前后端分离博客")
                .termsOfServiceUrl("http://www.example.com") // API服务条款
                .contact(new Contact("CM", "https://www.example.com", "example@xxx.com"))
                .version("1.0")
                .build();
    }
}
