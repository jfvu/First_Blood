package com.example.jf.cardapp.entity;

/**
 * Created by Administrator on 2017/5/8.
 */

public class CollectCard {
    private int id;
    private String pic;
    private String name;

    public CollectCard(int id, String pic, String name) {
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
        return "CollectCard{" +
                "id=" + id +
                ", pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
