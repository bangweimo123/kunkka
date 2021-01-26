package com.leshiguang.arch.kunkka.web.config;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-14 11:36
 * @Description
 */
public class AdminPlatformContextFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String region = httpServletRequest.getHeader("X-Region");
        AdminPlatfromContext context = new AdminPlatfromContext();
        context.setRegion(region);
        AdminPlatformContextHolder.set(context);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
