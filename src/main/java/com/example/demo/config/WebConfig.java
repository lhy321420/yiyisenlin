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
                .allowedOriginPatterns("*")  // 临时允许所有来源，测试通过后再收紧
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/upload/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns(
                        "/",                           // 根路径
                        "/index.html",                 // 首页
                        "/login.html",                 // 登录页
                        "/register.html",              // 注册页
                        "/user/login",                 // 登录接口 ⭐ 关键
                        "/user/register",              // 注册接口
                        "/static/**",                  // 静态资源
                        "/upload/**",                  // 上传文件
                        "/city/**",                    // 城市相关接口
                        "/**/*.html",                  // 所有 HTML
                        "/**/*.css",                   // 所有 CSS
                        "/**/*.js",                    // 所有 JS
                        "/**/*.png",                   // 所有图片
                        "/**/*.jpg",
                        "/**/*.jpeg",
                        "/**/*.gif"
                );
    }
}