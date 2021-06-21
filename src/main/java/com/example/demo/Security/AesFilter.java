package com.example.demo.Security;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class AesFilter implements Filter { //extends GenericFilterBean {

    public AesFilter() {}


    @Override
    public void doFilter(ServletRequest requestServ, ServletResponse responseServ,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) requestServ;

        AesRequestWrapper wrapper = new AesRequestWrapper(request, request.getParameterMap());

        filterChain.doFilter(wrapper, responseServ);
    }

}
