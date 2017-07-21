package com.example.jf.cardapp.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class GroupCardList {
    private int id;
    private String title;
    private List<GroupCard> list;

    public GroupCardList(int id, String title, List<GroupCard> list) {
        this.id = id;
        this.title = title;
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GroupCard> getList() {
        return list;
    }

    public void setList(List<GroupCard> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "GroupCardList{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", list=" + list +
                '}';
    }
}
