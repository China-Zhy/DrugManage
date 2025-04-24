package com.nxu.utils;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 过滤器: 判断用户是否登录, 未登录则只允许访问登陆注册和部分静态资源
 */
@WebFilter(filterName = "SessionFilter", urlPatterns = "/*")
@Component
public class SessionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // 获取请求的 URL
        String requestURI = httpRequest.getRequestURI();

        // 排除样式文件、登录页面、登录请求、注册页面、注册请求
        if (requestURI.startsWith("/error") || requestURI.startsWith("/image") || requestURI.startsWith("/layui") || requestURI.endsWith("/toUserLogin") || requestURI.endsWith("/doUserLogin") || requestURI.endsWith("/toUserEnroll") || requestURI.endsWith("/doUserEnroll") || requestURI.endsWith("/toNoticeDisabled")) {
            chain.doFilter(request, response);
            return;
        }

        // 检查 Session 中是否有用户信息
        if (session == null || session.getAttribute("loginUser") == null) {
            // 若没有用户信息，重定向到登录页面
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/toUserLogin");
        } else {
            // 若有用户信息，继续处理请求
            chain.doFilter(request, response);
        }
    }
}