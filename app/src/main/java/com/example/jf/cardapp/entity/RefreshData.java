package com.example.jf.cardapp.entity;


import java.util.List;

/**
 * Created by jf on 2017/2/28.
 */

public class RefreshData {

    /**
     * PageIndex : 1
     * PageSize : 20
     * Language : cn
     * CardSets : 卡片系列
     * Initial : ["B","D"]
     * Color : [1,2]
     * Category : ["sample string 1","sample string 2"]
     * Rarity : ["sample string 1","sample string 2"]
     * Power : [1,2]
     * Defense : [1,2]
     * Magic : ["3ww","2bb"]
     */

    private int PageIndex;
    private int PageSize;
    private String Language;
    private String CardSets;
    private List<String> Initial;
    private List<Integer> Color;
    private List<String> Category;
    private List<String> Rarity;
    private List<Integer> Power;
    private List<Integer> Defense;
    private List<String> Magic;

    public RefreshData(int pageIndex, int pageSize, String language, String cardSets, List<String> initial, List<Integer> color, List<String> category, List<String> rarity, List<Integer> power, List<Integer> defense, List<String> magic) {
        PageIndex = pageIndex;
        PageSize = pageSize;
        Language = language;
        CardSets = cardSets;
        Initial = initial;
        Color = color;
        Category = category;
        Rarity = rarity;
        Power = power;
        Defense = defense;
        Magic = magic;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int PageIndex) {
        this.PageIndex = PageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int PageSize) {
        this.PageSize = PageSize;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public String getCardSets() {
        return CardSets;
    }

    public void setCardSets(String CardSets) {
        this.CardSets = CardSets;
    }

    public List<String> getInitial() {
        return Initial;
    }

    public void setInitial(List<String> Initial) {
        this.Initial = Initial;
    }

    public List<Integer> getColor() {
        return Color;
    }

    public void setColor(List<Integer> Color) {
        this.Color = Color;
    }

    public List<String> getCategory() {
        return Category;
    }

    public void setCategory(List<String> Category) {
        this.Category = Category;
    }

    public List<String> getRarity() {
        return Rarity;
    }

    public void setRarity(List<String> Rarity) {
        this.Rarity = Rarity;
    }

    public List<Integer> getPower() {
        return Power;
    }

    public void setPower(List<Integer> Power) {
        this.Power = Power;
    }

    public List<Integer> getDefense() {
        return Defense;
    }

    public void setDefense(List<Integer> Defense) {
        this.Defense = Defense;
    }

    public List<String> getMagic() {
        return Magic;
    }

    public void setMagic(List<String> Magic) {
        this.Magic = Magic;
    }
}
