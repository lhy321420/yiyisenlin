package com.example.demo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        System.out.println("=== 拦截器拦截请求: " + uri + " ===");

        // 1. 放行所有 OPTIONS 预检（解决跨域）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            System.out.println("放行 OPTIONS 请求");
            return true;
        }

        // 2. 白名单路径直接放行
        if (uri.equals("/user/login") || uri.equals("/user/register")) {
            System.out.println("白名单放行: " + uri);
            return true;
        }

        // 3. 放行静态资源
        if (uri.endsWith(".html") || uri.endsWith(".css") || uri.endsWith(".js") ||
                uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".ico")) {
            System.out.println("放行静态资源: " + uri);
            return true;
        }

        // 4. 放行特定路径前缀
        if (uri.startsWith("/static/") || uri.startsWith("/upload/") || uri.startsWith("/city/")) {
            System.out.println("放行路径前缀: " + uri);
            return true;
        }

        // 5. 检查登录状态
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");

        if (loginUser == null) {
            System.out.println("未登录，拒绝访问: " + uri);
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"code\":401,\"msg\":\"未登录\"}");
            return false;
        }

        System.out.println("已登录，放行: " + uri);
        return true;
    }
}