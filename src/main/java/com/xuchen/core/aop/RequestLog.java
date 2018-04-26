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

@Aspect
@Component
public class RequestLog {

    private final static Logger logger = Logger.getLogger(RequestLog.class);

    @Pointcut("@annotation(com.xuchen.core.annotation.RequestLog)")
    void requestLog(){}

    @Before("requestLog()")
    void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL:[" + request.getRequestURI()+"],参数:"+Arrays.toString(joinPoint.getArgs()));
    }

}
