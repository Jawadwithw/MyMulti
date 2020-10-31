package com.xbbxsnia.the2806.Main2806.Main2806.Data;

import androidx.annotation.DrawableRes;

public class MenuAppData {
    private String desc;

    private String title;
    @DrawableRes
    private int featureImage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFeatureImage() {
        return featureImage;
    }

    public void setFeatureImage(int featureImage) {
        this.featureImage = featureImage;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
