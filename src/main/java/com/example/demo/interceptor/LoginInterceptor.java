package com.example.demo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行OPTIONS请求（CORS预检）
        System.out.println("=== 拦截器捕获请求: " + request.getRequestURI() + " ===");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String uri = request.getRequestURI();
        System.out.println("【拦截器】拦截请求: " + uri);

        // 检查登录状态
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");

        if (loginUser == null) {
            System.out.println("【拦截器】未登录，拒绝访问: " + uri);
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"code\":403,\"msg\":\"未登录，请先登录\"}");
            return false;
        }

        System.out.println("【拦截器】已登录，放行: " + uri);
        return true;
    }
}