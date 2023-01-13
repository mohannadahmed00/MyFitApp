package com.example.myfit.activities.models;

public class MealModel {
    String imgURL,name,des;
    boolean status;

    public MealModel(String imgURL, String name,boolean status) {
        this.imgURL = imgURL;
        this.name = name;
        this.status=status;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }

    public boolean isStatus() {
        return status;
    }
}
