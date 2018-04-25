package com.xuchen.core.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Aspect
@Component
public class RequestLog {

    private static Set<String> set;
    private final static Logger logger = Logger.getLogger(RequestLog.class);
    static {
        set = new HashSet<>();
        set.add("index");
        set.add("toAdd");
        set.add("toEdit");
        set.add("list");
    }

    @Pointcut("execution(* com.xuchen.controller.*.*(..)) || execution(* com.xuchen.controller.sys.*.*(..))")
    void webLog(){}

    @Before("webLog()")
    void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        if (!set.contains(joinPoint.getSignature().getName())){
            logger.info("URL:" + request.getRequestURL().toString());
            logger.info("参数:"+Arrays.toString(joinPoint.getArgs()));
        }
//        System.out.println("URL : " + request.getRequestURL().toString());
//        System.out.println("HTTP_METHOD : " + request.getMethod());
//        System.out.println("IP : " + request.getRemoteAddr());
//        System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

}
