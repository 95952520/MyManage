package com.xuchen.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验对象是否为null
 * 校验方法的第一个参数须为该对象,前端须传updateFiled作为更新的字段名
 * checkFiled为校验不能为空的字段
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckNullUpdate {
    String[] checkFiled();
}
