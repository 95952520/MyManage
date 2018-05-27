package com.xuchen.entity.base;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xuchen.base.BaseQuery;
import com.xuchen.util.MyUtils;

import java.util.Date;

public class MyEntityWrapper<T> extends EntityWrapper {

    private T myEntity;
    private BaseQuery baseQuery;

    public MyEntityWrapper() {
    }

    public MyEntityWrapper(BaseQuery baseQuery, T myEntity) {
        this.myEntity = myEntity;
        if (baseQuery == null) {
            baseQuery = new BaseQuery();
        }
        this.baseQuery = baseQuery;
        baseQuery.startPage();
        this.orderBy(baseQuery.getPageField(), "asc".equals(baseQuery.getPageOrder()));
    }

    public MyEntityWrapper like(String column) {
        Object columnValue = MyUtils.getFieldValue(myEntity,getFiled(column));
        if (columnValue != null) {
            super.like(column, String.valueOf(columnValue));
        }
        return this;
    }

    public MyEntityWrapper eq(String column) {
        Object columnValue = MyUtils.getFieldValue(myEntity,getFiled(column));
        if (columnValue != null) {
            super.eq(column, columnValue);
        }
        return this;
    }

    public MyEntityWrapper between(String column) {
        Date beginDate = baseQuery.getBeginDate();
        Date endDate = baseQuery.getEndDate();
        if (beginDate!=null && endDate!=null){
            if (beginDate.before(endDate)){
                super.between(column,beginDate,endDate);
            }
        }else if (beginDate!=null){
            super.gt(column,beginDate);
        }else if (baseQuery.getEndDate()!=null){
            super.lt(column,endDate);
        }
        return this;
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
