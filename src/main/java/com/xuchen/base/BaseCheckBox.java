package com.xuchen.base;

/**
 * 表格内checkbox
 */
public class BaseCheckBox {
    private Integer id;
    private String filed;
    private boolean checked;

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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "BaseCheckBox{" +
                "id=" + id +
                ", filed='" + filed + '\'' +
                ", checked=" + checked +
                '}';
    }
}
