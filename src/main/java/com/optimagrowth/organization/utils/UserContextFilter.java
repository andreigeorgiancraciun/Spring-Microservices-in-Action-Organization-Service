package com.optimagrowth.organization.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.optimagrowth.organization.utils.UserContext.*;

@Component
public class UserContextFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(CORRELATION_ID));
        UserContextHolder.getContext().setUserId(httpServletRequest.getHeader(USER_ID));
        UserContextHolder.getContext().setAuthToken(httpServletRequest.getHeader(AUTH_TOKEN));
        UserContextHolder.getContext().setOrganizationId(httpServletRequest.getHeader(ORGANIZATION_ID));

        logger.debug("Organization Service Incoming Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        filterChain.doFilter(httpServletRequest, servletResponse);
    }
}
