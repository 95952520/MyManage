package com.xuchen.entity.base;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xuchen.base.BaseQuery;

import java.lang.reflect.Method;

public class MyEntityWrapper<T> extends EntityWrapper {

    private T myEntity;

    public MyEntityWrapper() {
    }

    public MyEntityWrapper(BaseQuery baseQuery, T myEntity) {
        this.myEntity=myEntity;
        if (baseQuery==null){
            baseQuery=new BaseQuery();
        }
        baseQuery.startPage();
        this.orderBy(baseQuery.getPageField(), "asc".equals(baseQuery.getPageOrder()));
    }

    public MyEntityWrapper like(String column) {
        String columnValue = getColumnValue(column);
        if (columnValue != null) {
            super.like(column, columnValue);
        }
        return this;
    }

    public MyEntityWrapper eq(String column) {
        String columnValue = getColumnValue(column);
        if (columnValue != null) {
            super.eq(column, columnValue);
        }
        return this;
    }

    private String getColumnValue(String column) {
        if (myEntity == null) {
            return null;
        }
        Method method = null;
        try {
            method = myEntity.getClass().getMethod(getFiled("get_" + column), new Class[0]);
            Object value = method.invoke(myEntity, new Object[]{});
            if (value == null || "".equals(value)) {
                return null;
            }
            return String.valueOf(value);
        } catch (Exception e) {
            System.out.println("数据库对象映射entity失败:" + myEntity.getClass());
        }
        return null;
    }


    private static String getFiled(String string) {
        StringBuilder column = new StringBuilder();
        string = string.toLowerCase();
        for (int i = 0; i < string.length(); i++) {
            if ("_".equals(String.valueOf(string.charAt(i)))) {
                column.append(Character.toUpperCase(string.charAt(++i)));
                continue;
            }
            column.append(string.charAt(i));
        }
        return column.toString();
    }

}
