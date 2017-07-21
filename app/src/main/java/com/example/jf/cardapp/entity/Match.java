package com.example.jf.cardapp.entity;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/2/20.
 */

public class Match {
    private int id;
    private String  picture;
    private String name;
    private String place;
    private String date;
    private String content;

    public Match(int id, String picture, String name, String place, String date, String content) {
        this.id = id;
        this.picture = picture;
        this.name = name;
        this.place = place;
        this.date = date;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
