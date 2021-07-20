//package com.hongkun.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import java.io.IOException;
//
//@Slf4j
////@WebFilter(urlPatterns = "/webFileter")
//@WebFilter(urlPatterns = {"/webjars/*","/*","/checkDatabase/*","/im/*","/privacy/*","/config/*"}, filterName = "accessLog")
//@Component
//public class AccessLogFilter implements Filter {
//
//    @Value("${server.tomcat.accesslog.condition-unless}")
//    private String conditionUnless;
//
//
//    public void destroy() {
//    }
//
//    @Override
//    public void doFilter( ServletRequest request,ServletResponse response, FilterChain chain)throws IOException, ServletException{
//
//        log.info("conditionUnless:"+conditionUnless);
//
//        request.setAttribute("accessLog", "NO_LOG");
//        chain.doFilter(request, response);
//    }
//
//
//    public void init(FilterConfig config) throws ServletException {
//
//    }
//
//
//}