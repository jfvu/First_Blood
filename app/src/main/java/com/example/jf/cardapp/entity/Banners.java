package com.example.jf.cardapp.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/6.
 */

public class Banners implements Serializable {
    /**
     * Type : 1
     * Pic : /News/20170202.jpg
     * Title : 中资企业在缅甸遭冲击 中国使馆提严正交涉
     * Url : http://world.huanqiu.com/article/2017-02/10192048.html?from=bdwz
     */

    private int Type;
    private String Pic;
    private String Title;
    private String Url;

    public Banners(int type, String pic, String title, String url) {
        Type = type;
        Pic = pic;
        Title = title;
        Url = url;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String Pic) {
        this.Pic = Pic;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    @Override
    public String toString() {
        return "Banners{" +
                "Type=" + Type +
                ", Pic='" + Pic + '\'' +
                ", Title='" + Title + '\'' +
                ", Url='" + Url + '\'' +
                '}';
    }
}
