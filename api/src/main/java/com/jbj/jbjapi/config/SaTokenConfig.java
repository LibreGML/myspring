package com.jbj.jbjapi.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * sa-token 配置
 *
 * @author mhw
 */
@RequiredArgsConstructor
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    private final String[] excludePath = new String[]{
            "/doc.html",
            "/error",
            "/webjars/**",
            "/swagger-resources",
            "/v2/api-docs",

            "/website/**",
            "/localFile/upload",
            "/account/login",
            "/official/**",
            "/business/return_url",
            "/business/yue/return_url",
            "/open/**"
    };

    /**
     * 注册sa-token的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义验证规则
        registry.addInterceptor(new SaRouteInterceptor((request, response, handler) -> {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "content-type,Authorization");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            // 登录验证 -- 排除多个路径
            SaRouter
                    // 获取所有的
                    .match("/**")
                    // 排除下不需要拦截的
                    .notMatch(excludePath)
                    .check(() -> {
                        LoginHelper.getLoginUser();
                    });
        }) {
            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                LoginHelper.clearCache();
            }
        }).addPathPatterns("/**");
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
    }


}
