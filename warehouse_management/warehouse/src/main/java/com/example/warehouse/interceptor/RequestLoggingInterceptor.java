package com.example.warehouse.interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception  {
        String sessionId = request.getSession().getId();
        logger.info("Session ID: {} - Request to {} started", sessionId, request.getRequestURI());
        return true;
    }

    
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String sessionId = request.getSession().getId();
        logger.info("Session ID: {} - Request to {} completed", sessionId, request.getRequestURI());
    }

    
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            logger.error("Request to {} resulted in exception", request.getRequestURI(), ex);
        }
    }
}
