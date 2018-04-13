package com.xuchen.model;

import java.util.List;

public class ParentMenu {
    private Integer id;
    private String name;
    private String img;
    private String parentStatus;
    private List<SonMenu> list;

    private String url;
    private String perms;
    private Integer orderNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getParentStatus() {
        return parentStatus;
    }

    public void setParentStatus(String parentStatus) {
        this.parentStatus = parentStatus;
    }

    public List<SonMenu> getList() {
        return list;
    }

    public void setList(List<SonMenu> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ParentMenu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", parentStatus='" + parentStatus + '\'' +
                ", list=" + list +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
