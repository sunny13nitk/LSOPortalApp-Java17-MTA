package com.sap.cap.esmapi.config;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsrfFilter extends OncePerRequestFilter
{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String csrfToken = request.getHeader("X-CSRF-Token");
        log.info("#CSRF Filter check for : " + request.getMethod());

        if (csrfToken == null || csrfToken.isEmpty())
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        log.info("#CSRF Filter generated for : " + request.getMethod());
        filterChain.doFilter(request, response);
    }
}
