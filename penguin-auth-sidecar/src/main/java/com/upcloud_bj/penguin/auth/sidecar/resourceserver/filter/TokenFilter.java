package com.upcloud_bj.penguin.auth.sidecar.resourceserver.filter;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {

    private static JacksonJsonParser jsonParser = new JacksonJsonParser();

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorization = request.getHeader("Authorization");
        String token = null;
        Map<String, Object> tokenSession = null;

        if (!StringUtils.isEmpty(authorization)) {
            if (authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);
            }
        }
        if (token == null) {
            token = request.getParameter("access_token");
        }
        if (token != null) {
            Jwt data = JwtHelper.decode(token);
            String claims = data.getClaims();

            Map<String, Object> claMap = jsonParser.parseMap(claims);
            tokenSession = ((Map<String, Object>) claMap.get("session"));

            request.setAttribute("tokenSession", tokenSession);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }
}
