package com.example.jf.cardapp.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class Curve {
    private List<String> date;
    private List<Integer> score;

    public Curve(List<String> date, List<Integer> score) {
        this.date = date;
        this.score = score;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<Integer> getScore() {
        return score;
    }

    public void setScore(List<Integer> score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Curve{" +
                "date=" + date +
                ", score=" + score +
                '}';
    }
}
