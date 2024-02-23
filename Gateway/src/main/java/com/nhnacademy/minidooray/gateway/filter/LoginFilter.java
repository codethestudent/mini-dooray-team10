package com.nhnacademy.minidooray.gateway.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class LoginFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String reqUrl = req.getRequestURI();

        HttpSession session = req.getSession(false);
        log.info("--------{}", session == null);

        try {
                if(Objects.isNull(session) || session.getAttribute("userId") == null) {
                log.info("미인증 사용자 요청 : {}", reqUrl);
                resp.sendRedirect("/index");
            }
            filterChain.doFilter(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("인증 체크 필터 종료");
        }

    }
}
