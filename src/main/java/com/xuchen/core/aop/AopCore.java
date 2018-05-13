package com.xuchen.core.aop;

import com.xuchen.core.annotation.CheckNullUpdate;
import com.xuchen.util.MyUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class AopCore {

    private final static Logger logger = Logger.getLogger(AopCore.class);

    @Pointcut("@annotation(com.xuchen.core.annotation.RequestLog)")
    void requestLog() {
    }

    @Pointcut("@annotation(com.xuchen.core.annotation.CheckNullUpdate) ")
    void checkNullUpdate() {
    }

    @Before("requestLog()")
    void requestLog(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL:[" + request.getRequestURI() + "],参数:" + Arrays.toString(joinPoint.getArgs()));
    }

    @Before(value = "checkNullUpdate()")
    void CheckNullUpdate(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String updateFiled = request.getParameter("updateField");
        Object myEntity = joinPoint.getArgs()[0];
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        CheckNullUpdate check = method.getAnnotation(CheckNullUpdate.class);
        if (check != null) {
            String[] checkFileds = check.checkFiled();
            for (String checkFiled : checkFileds) {
                if (checkFiled.equals(updateFiled)) {
                    Object value = getColumnValue(myEntity, updateFiled);
                    if (MyUtils.isEmpty(value)) {
                        throw new RuntimeException("该字段不能为空");
                    }
                    break;
                }
            }
        }
    }


    private Object getColumnValue(Object myEntity, String column) {
        Field fields[] = myEntity.getClass().getDeclaredFields();
        Field.setAccessible(fields, true);
        Object value = null;
        for (Field field : fields) {
            if (column.equals(field.getName())) {
                try {
                    value = field.get(myEntity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return value;
    }

}
