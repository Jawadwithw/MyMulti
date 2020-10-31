package com.xbbxsnia.the2806.ZipJet.models;

import java.util.List;

public class Laundry {
    private List<String> imagesUrl;
    private float rate;
    private int distance;
    private String avatarUrl;
    private String name;
    private String address;
    private String phoneNumber;
    private int[] rateNumbersPercent;
    private List<LaundryComment> comments;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int[] getRateNumbersPercent() {
        return rateNumbersPercent;
    }

    public void setRateNumbersPercent(int[] rateNumbersPercent) {
        this.rateNumbersPercent = rateNumbersPercent;
    }

    public List<LaundryComment> getComments() {
        return comments;
    }

    public void setComments(List<LaundryComment> comments) {
        this.comments = comments;
    }
}
