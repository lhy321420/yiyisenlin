package com.example.demo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 添加日志：打印所有请求
        System.out.println("=== 拦截器拦截请求: " + request.getRequestURI() + " ===");

        // 1. 放行所有 OPTIONS 预检（解决跨域 403）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            System.out.println("放行 OPTIONS 请求");
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
            System.out.println("白名单放行: " + uri);
            return true;
        }

        // 3. 校验登录状态
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");

        if (loginUser == null) {
            System.out.println("未登录，拒绝访问: " + uri);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"未登录\"}");
            return false;
        }

        System.out.println("已登录，放行: " + uri);
        return true;
    }
}