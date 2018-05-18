package com.xuchen.model.base;

import java.util.ArrayList;
import java.util.List;

/**
 * layui-xtree 3.0版本 见
 * http://fly.layui.com/jie/19038/
 */
public class LayuiTreeModel {
    private Integer value;
    private String title;
    private boolean checked;
    private boolean disabled;
    private List<LayuiTreeModel> data;

    public LayuiTreeModel(){
        data = new ArrayList<>();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LayuiTreeModel> getData() {
        return data;
    }

    public void setData(List<LayuiTreeModel> data) {
        this.data = data;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "TreeParModel{" +
                "value='" + value + '\'' +
                ", title='" + title + '\'' +
                ", checked=" + checked +
                ", disabled=" + disabled +
                ", data=" + data +
                '}';
    }
}
