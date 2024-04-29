package com.example.warehouse.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.warehouse.interceptor.RequestLoggingInterceptor;
import com.example.warehouse.util.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthorizeFilter extends OncePerRequestFilter {
    // 自定义filter加入security的filer中
    // once表示每次请求检查
    // 确保header中携带正确的jwt
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);
    @Resource
    JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        // 跳过不需要JWT的端点
        if (path.startsWith("/users/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authorization = request.getHeader("Authorization");
        DecodedJWT decodedJWT = jwtUtils.resolveJWT(authorization);

        if (decodedJWT != null) {
            UserDetails userDetails = jwtUtils.decodedJwtToUser(decodedJWT);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            String username = decodedJWT.getClaim("username").asString();
            logger.info("User {} is making a request to {}", username, request.getRequestURI());
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Token is invalid or expired\", \"code\": 401}");
            return; // 注意：这里提前返回，不再调用filterChain.doFilter
        }
    }

    // @Override
    // protected void doFilterInternal(HttpServletRequest request,
    // HttpServletResponse response, FilterChain filterChain) throws
    // ServletException, IOException {
    // String authorization = request.getHeader("Authorization");//取出请求头中的jwtToken
    // DecodedJWT decodedJWT = jwtUtils.resolveJWT(authorization);

    // if(decodedJWT!=null) {
    // UserDetails userDetails = jwtUtils.decodedJwtToUser(decodedJWT);
    // //下面的这个类我不清楚是什么，它应该用于认证jwt是否正确，并将认证结果交给security
    // UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
    // new UsernamePasswordAuthenticationToken(userDetails, null,
    // userDetails.getAuthorities());
    // usernamePasswordAuthenticationToken.setDetails(new
    // WebAuthenticationDetailsSource().buildDetails(request));
    // //以上2步获得认证结果，下面交给security
    // SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

    // //这里将id返回方便后续业务，不知道有啥用，感觉可有可无
    // request.setAttribute("id", jwtUtils.decodedJwtToId(decodedJWT));
    // }
    // filterChain.doFilter(request, response);
    // }
}
