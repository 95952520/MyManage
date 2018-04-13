package com.xuchen.model;

import java.io.Serializable;

public class SonMenu implements Serializable{

    private static final long serialVersionUID = 1L;
    private Integer sonId;
    private String sonName;
    private String sonUrl;
    private Integer parentId;
    private Integer sonStatus;

    private String sonImg;
    private String sonPerms;
    private Integer sonOrderNum;

    public Integer getSonId() {
        return sonId;
    }

    public void setSonId(Integer sonId) {
        this.sonId = sonId;
    }

    public String getSonName() {
        return sonName;
    }

    public void setSonName(String sonName) {
        this.sonName = sonName;
    }

    public String getSonUrl() {
        return sonUrl;
    }

    public void setSonUrl(String sonUrl) {
        this.sonUrl = sonUrl;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSonStatus() {
        return sonStatus;
    }

    public void setSonStatus(Integer sonStatus) {
        this.sonStatus = sonStatus;
    }

    @Override
    public String toString() {
        return "SonMenu{" +
                "sonId=" + sonId +
                ", sonName='" + sonName + '\'' +
                ", sonUrl='" + sonUrl + '\'' +
                ", parentId=" + parentId +
                ", sonStatus=" + sonStatus +
                '}';
    }

    public String getSonImg() {
        return sonImg;
    }

    public void setSonImg(String sonImg) {
        this.sonImg = sonImg;
    }

    public String getSonPerms() {
        return sonPerms;
    }

    public void setSonPerms(String sonPerms) {
        this.sonPerms = sonPerms;
    }

    public Integer getSonOrderNum() {
        return sonOrderNum;
    }

    public void setSonOrderNum(Integer sonOrderNum) {
        this.sonOrderNum = sonOrderNum;
    }
}
