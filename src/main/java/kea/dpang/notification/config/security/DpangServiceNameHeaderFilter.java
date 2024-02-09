package kea.dpang.notification.config.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DpangServiceNameHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String headerValue = httpServletRequest.getHeader("X-DPANG-SERVICE-NAME");

        if (headerValue == null) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "X-DPANG-SERVICE-NAME header is missing");
            return;
        }

        chain.doFilter(request, response);
    }
}
