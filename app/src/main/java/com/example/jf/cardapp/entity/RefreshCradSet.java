package com.example.jf.cardapp.entity;

/**
 * Created by jf on 2017/2/21.
 */

public class RefreshCradSet {
    private String name;
    private  String type;

    public RefreshCradSet(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", type='" + type + '\'' +
                '}';
    }
}
