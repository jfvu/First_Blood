package com.example.jf.cardapp.entity;

/**
 * Created by jf on 2017/2/28.
 */

public class RefreshCardType1 {
    private String name;
    private Boolean select;
    private  String type;

    public RefreshCardType1(String name, Boolean select, String type) {
        this.name = name;
        this.select = select;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RefreshCradColor{" +
                "name='" + name + '\'' +
                ", select=" + select +
                ", type='" + type + '\'' +
                '}';
    }
}
