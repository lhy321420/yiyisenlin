package com.example.demo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 放行所有 OPTIONS 预检（解决跨域 403）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String uri = request.getRequestURI();

        // 2. 白名单路径直接放行（双保险）
        boolean isWhiteList = uri.equals("/user/login")
                || uri.equals("/user/register")
                || uri.startsWith("/user/login/")
                || uri.startsWith("/user/register/")
                || uri.equals("/")
                || uri.endsWith(".html")
                || uri.startsWith("/static/")
                || uri.startsWith("/upload/")
                || uri.startsWith("/city/");

        if (isWhiteList) {
            return true;
        }

        // 3. 校验登录状态
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");

        if (loginUser == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"未登录\"}");
            return false;
        }

        return true;
    }
}