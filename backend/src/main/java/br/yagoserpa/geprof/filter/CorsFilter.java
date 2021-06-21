package br.yagoserpa.geprof.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CorsFilter implements Filter {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(this);
        registration.addUrlPatterns("/**/*", "/*");
        registration.setName("corsFilter");
        registration.setOrder(2);
        return registration;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
