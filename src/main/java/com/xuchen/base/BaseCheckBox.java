package com.xuchen.base;

public class BaseCheckBox {
    private Integer id;
    private String filed;
    private boolean flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "BaseCheckBox{" +
                "id=" + id +
                ", filed='" + filed + '\'' +
                ", flag=" + flag +
                '}';
    }
}
