package com.udea.bancodigital.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

// @Order fuerza a que este filtro sea el PRIMERO en ejecutarse,
// incluso antes que la cadena de filtros de Spring Security --
// asi el traceId ya existe cuando cualquier otro filtro lo necesite.
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceIdFilter extends OncePerRequestFilter {

    public static final String TRACE_ID_ATTR = "traceId";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String traceId = UUID.randomUUID().toString();
        request.setAttribute(TRACE_ID_ATTR, traceId);
        response.setHeader("X-Trace-Id", traceId);

        filterChain.doFilter(request, response);
    }
}