package com.example.demo.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AesFilter implements Filter { //extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest requestServ, ServletResponse responseServ,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) requestServ;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("MY FILTER!!!\n");

        System.out.println(authentication.getCredentials());

       /* Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_USER")) {
            request.getSession().setAttribute("myVale", "myvalue");
        }*/

        filterChain.doFilter(request, responseServ);
    }

}
