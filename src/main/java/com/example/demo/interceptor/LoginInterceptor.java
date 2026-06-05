package com.example.demo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 放行所有 OPTIONS 跨域预检（解决 403）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 2. 放行所有需要公开访问的路径
        String uri = request.getRequestURI();
        if (
                uri.equals("/") ||
                        uri.equals("/user/login") ||
                        uri.equals("/user/register") ||
                        uri.contains("/city/") ||
                        uri.contains("/static/") ||
                        uri.contains("/upload/") ||
                        uri.endsWith(".html")
        ) {
            return true;
        }

        // 3. 检查登录状态
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");

        if (loginUser == null) {
            response.setStatus(200); // 重要：不要返回401，前端会报错
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"未登录\"}");
            return false;
        }

        return true;
    }
}