package br.yagoserpa.geprof.filter;

import br.yagoserpa.geprof.model.Auth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Configuration
public class JWTFilter implements Filter {

    private final String secret;

    public JWTFilter(
        @Value("${jwtsecret}") String secret
    ) {
        this.secret = secret;
    }

    @Bean
    public FilterRegistrationBean<JWTFilter> jwtFilterRegistration() {
        FilterRegistrationBean<JWTFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(this);
        registration.addUrlPatterns("/**/*", "/*");
        registration.setName("jwtFilter");
        registration.setOrder(3);
        return registration;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;

        if (
            Objects.equals(httpRequest.getRequestURI(), "/api/v1/auth") ||
            httpRequest.getRequestURI().startsWith("/api/v1/public") ||
            Objects.equals(httpRequest.getMethod(), "OPTIONS")
        ) {
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        }

        var authorization = httpRequest.getHeader("Authorization");

        if (authorization == null) {
            sendUnauthorized(httpRequest, httpResponse);
            return;
        }

        var parts = authorization.split(" ");

        if (parts.length < 2) {
            sendUnauthorized(httpRequest, httpResponse);
            return;
        }
        if (!Objects.equals(parts[0], "Bearer")) {
            sendUnauthorized(httpRequest, httpResponse);
            return;
        }

        try {
            var body = Jwts.parser()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(parts[1])
                    .getBody();

            var auth = new Auth();
            auth.setId((long) body.get("id", Integer.class));

            httpRequest.setAttribute("auth", auth);

            filterChain.doFilter(httpRequest, httpResponse);
        } catch (Exception e) {
            sendUnauthorized(httpRequest, httpResponse);
        }
    }

    public void sendUnauthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().println("{" +
                "\"status\": "  + HttpStatus.UNAUTHORIZED.value() + "," +
                "\"path\": \"" + request.getRequestURI() + "\"," +
                "}");
    }

}
