package com.jbj.jbjapi.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.PostConstruct;

/**
 * Swagger 文档配置
 *
 * @author mhw
 */
@RequiredArgsConstructor
@Configuration
@EnableKnife4j
public class SwaggerConfig {

    @Value("${spring.profiles.active}")
    private String active;

    /**
     * 配置文档生成最佳实践
     *
     * @return
     */
    @Bean
    public Docket createRestApiOpen() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .groupName("对外开放接口")
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描指定包中的swagger注解
                .apis(RequestHandlerSelectors.basePackage("com.jbj.jbjapi.controller.openApi"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createRestApiSys() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(!active.equals("prod"))
                .groupName("sys")
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描指定包中的swagger注解
                .apis(RequestHandlerSelectors.basePackage("com.jbj.jbjapi.controller.sys"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createRestApiOfficial() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(!active.equals("prod"))
                .groupName("官网")
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描指定包中的swagger注解
                .apis(RequestHandlerSelectors.basePackage("com.jbj.jbjapi.controller.official"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createRestApiJbj() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(!active.equals("prod"))
                .groupName("后台")
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描指定包中的swagger注解
                .apis(RequestHandlerSelectors.basePackage("com.jbj.jbjapi.controller.backstage"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }


    /**
     * 配置基本信息
     *
     * @return
     */
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("jbj")
                .description("jbj api")
                .contact(new Contact("mhw", "", ""))
                .version("1.0")
                .build();
    }

}
