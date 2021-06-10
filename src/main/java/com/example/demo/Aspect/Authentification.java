package com.example.demo.Aspect;

import com.example.demo.Exception.UnauthorizationException;
import com.example.demo.Entity.RoleType;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Authentification {

    @Autowired
    UserRepository userRepository;

    //"execution(public String ru.sysout.aspectsdemo.service.FullNameComposer.*(..))"
    @Pointcut("execution(* com.example.demo.Controller.HelloController.getUserById(..))")
    public void serviceMethods() {
    };

    @Before("serviceMethods()")
    public void checkRole(JoinPoint joinPoint) throws Exception {
        String name = (String) joinPoint.getArgs()[1];
        User user = userRepository.findAllByName(name);
        if(user.getRole() == RoleType.DEVELOPER) {
            //throw new Exception("ex");
            throw new UnauthorizationException();
        }
    }
}
