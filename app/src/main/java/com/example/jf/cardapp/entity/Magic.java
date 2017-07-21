package com.example.jf.cardapp.entity;

/**
 * Created by leen on 2017/2/21.
 */

public class Magic {
    private int id;
    private String name;
    private String nameen;
    private String pic;
    private String describe;
    private String purpose;
    private String category;
    private String magic;
    private String rarity;
    private String version;
    private String edition;
    private String illus;
    private String language;

    public Magic(int id, String name, String nameen, String pic, String describe, String purpose, String category, String magic, String rarity, String version, String edition, String illus, String language) {
        this.id = id;
        this.name = name;
        this.nameen = nameen;
        this.pic = pic;
        this.describe = describe;
        this.purpose = purpose;
        this.category = category;
        this.magic = magic;
        this.rarity = rarity;
        this.version = version;
        this.edition = edition;
        this.illus = illus;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameen() {
        return nameen;
    }

    public void setNameen(String nameen) {
        this.nameen = nameen;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMagic() {
        return magic;
    }

    public void setMagic(String magic) {
        this.magic = magic;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getIllus() {
        return illus;
    }

    public void setIllus(String illus) {
        this.illus = illus;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Magic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameen='" + nameen + '\'' +
                ", pic='" + pic + '\'' +
                ", describe='" + describe + '\'' +
                ", purpose='" + purpose + '\'' +
                ", category='" + category + '\'' +
                ", magic='" + magic + '\'' +
                ", rarity='" + rarity + '\'' +
                ", version='" + version + '\'' +
                ", edition='" + edition + '\'' +
                ", illus='" + illus + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
