package com.example.jf.cardapp.entity;

import java.io.Serializable;

/**
 * Created by jf on 2017/2/26.
 */

public class PeopleData implements Serializable {
    private String UserName;
    private String Name;
    private String Email;
    private String Phone;
    private int Gender;
    private int Age;
    private int GameAge;
    private String PeopleId;
    private String Address;
    private String Career;
    private String Photo;
    private String AlipayID;
    private String WeixinID;

    public PeopleData() {
        super();
    }

    public PeopleData(String userName, String name, String email, String phone, int gender, int age, int gameAge, String peopleId, String address, String career, String photo, String alipayID, String weixinID) {
        UserName = userName;
        Name = name;
        Email = email;
        Phone = phone;
        Gender = gender;
        Age = age;
        GameAge = gameAge;
        PeopleId = peopleId;
        Address = address;
        Career = career;
        Photo = photo;
        AlipayID = alipayID;
        WeixinID = weixinID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getGameAge() {
        return GameAge;
    }

    public void setGameAge(int gameAge) {
        GameAge = gameAge;
    }

    public String getPeopleId() {
        return PeopleId;
    }

    public void setPeopleId(String peopleId) {
        PeopleId = peopleId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCareer() {
        return Career;
    }

    public void setCareer(String career) {
        Career = career;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getAlipayID() {
        return AlipayID;
    }

    public void setAlipayID(String alipayID) {
        AlipayID = alipayID;
    }

    public String getWeixinID() {
        return WeixinID;
    }

    public void setWeixinID(String weixinID) {
        WeixinID = weixinID;
    }

    @Override
    public String toString() {
        return "PeopleData{" +
                "UserName='" + UserName + '\'' +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Gender=" + Gender +
                ", Age=" + Age +
                ", GameAge=" + GameAge +
                ", PeopleId='" + PeopleId + '\'' +
                ", Address='" + Address + '\'' +
                ", Career='" + Career + '\'' +
                ", Photo='" + Photo + '\'' +
                ", AlipayID='" + AlipayID + '\'' +
                ", WeixinID='" + WeixinID + '\'' +
                '}';
    }
}
