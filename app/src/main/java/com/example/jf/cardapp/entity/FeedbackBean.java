package com.example.jf.cardapp.entity;

/**
 * Created by jf on 2017/3/9.
 */

public class FeedbackBean {
    /**
     * Title : sample string 1
     * Content : sample string 2
     * Type : 0
     */

    private String Title;
    private String Content;
    private int Type;

    public FeedbackBean(String title, String content, int type) {
        Title = title;
        Content = content;
        Type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }
}
