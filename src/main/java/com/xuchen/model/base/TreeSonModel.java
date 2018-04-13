package com.xuchen.model.base;

import java.util.ArrayList;
import java.util.List;

public class TreeSonModel {
    private Integer value;
    private String title;
    private boolean checked;
    private boolean disabled;
    private List<TreeSonModel> data;

    public TreeSonModel(){
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

    public List<TreeSonModel> getData() {
        return data;
    }

    public void setData(List<TreeSonModel> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TreeSonModel{" +
                "value='" + value + '\'' +
                ", title='" + title + '\'' +
                ", checked=" + checked +
                ", disabled=" + disabled +
                ", data=" + data +
                '}';
    }
}
