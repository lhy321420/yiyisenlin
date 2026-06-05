package com.example.demo.config;

import com.example.demo.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("https://lhy321420.github.io", "http://localhost:*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    // 原有静态资源不动
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/upload/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    // 拦截器配置原样保留，放行登录接口已经配好了不用修改
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login.html",
                        "/register.html",
                        "/user/login",
                        "/user/register",
                        "/",
                        "/index.html",
                        "/user-info.html",
                        "/user/update-info",
                        "/**/*.html",
                        "/static/**",
                        "/city/**",
                        "/upload/**"
                );
    }
}