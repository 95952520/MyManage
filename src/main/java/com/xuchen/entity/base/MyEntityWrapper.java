package com.xuchen.entity.base;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xuchen.base.BaseQuery;
import com.xuchen.util.MyUtils;

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
        String columnValue = (String) MyUtils.getFieldValue(myEntity,getFiled(column));
        if (columnValue != null) {
            super.like(column, columnValue);
        }
        return this;
    }

    public MyEntityWrapper eq(String column) {
        String columnValue = (String) MyUtils.getFieldValue(myEntity,getFiled(column));
        if (columnValue != null) {
            super.eq(column, columnValue);
        }
        return this;
    }

    public MyEntityWrapper between(String column) {
        if (baseQuery.getBeginDate()!=null && baseQuery.getEndDate()!=null){
            if (baseQuery.getBeginDate().before(baseQuery.getEndDate())){
                super.between(column,baseQuery.getBeginDate(),baseQuery.getEndDate());
            }
        }else if (baseQuery.getBeginDate()!=null){
            super.gt(column,baseQuery.getBeginDate());
        }else if (baseQuery.getEndDate()!=null){
            super.lt(column,baseQuery.getEndDate());
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
