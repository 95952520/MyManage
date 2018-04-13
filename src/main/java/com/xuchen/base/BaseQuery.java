package com.xuchen.base;


import com.baomidou.mybatisplus.plugins.pagination.PageHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseQuery {
    //当前页码 从1开始
    private Integer page = 1;
    //每页显示条数
    private Integer limit = 15;
    //排序字段
    private String pageField;
    //排序方式
    private String pageOrder;
    //结束时间
    private String beginDate;
    //开始时间
    private String endDate;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getPageField() {
        return pageField;
    }

    public void setPageField(String pageField) {
        this.pageField = pageField;
    }

    public String getPageOrder() {
        return pageOrder;
    }

    public void setPageOrder(String pageOrder) {
        this.pageOrder = pageOrder;
    }

    public void startPage() {
        PageHelper.startPage(this.page, this.limit);
    }

    public Date getBeginDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        if (beginDate == null){
            return null;
        }
        try {
            if (beginDate.length() == 10) {
                date = dateFormat.parse(beginDate + " 00:00:00");
            } else {
                date = dateFormat.parse(beginDate);
            }
        } catch (ParseException e) {
            System.out.println("String转化Date失败!!!");
        }
        return date;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        if (endDate == null){
            return null;
        }
        try {
            if (endDate.length() == 10) {
                date = dateFormat.parse(endDate + " 23:59:59");
            } else {
                date = dateFormat.parse(endDate);
            }
        } catch (ParseException e) {
            System.out.println("String转化Date失败!!!");
        }
        return date;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "BaseQuery{" +
                "page=" + page +
                ", limit=" + limit +
                ", pageField='" + pageField + '\'' +
                ", pageOrder='" + pageOrder + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
