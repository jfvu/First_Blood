package com.example.jf.cardapp.entity;

/**
 * Created by Administrator on 2017/5/15.
 */

public class GroupCard {
    private int id;
    private String pic;
    private String name;

    public GroupCard(int id, String pic, String name) {
        this.id = id;
        this.pic = pic;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GroupCard{" +
                "id=" + id +
                ", pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
