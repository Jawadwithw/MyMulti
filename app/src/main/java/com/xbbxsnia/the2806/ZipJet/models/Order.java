package com.xbbxsnia.the2806.ZipJet.models;

import java.util.List;

public class Order {
    private String imageUrl;
    private List<ClothNumberModel> clothesModel;
    private List<String> services;
    private int price = 0;
    private String description;
    private String title;
    private String full_name;
    private int temperature;
    private String temperatureUnit;
    private String color;
    private String state;
    private String deliverAvatar;
    private String deliverName;
    private int deliveryPrice;
    private String plateNumber;
    private String deliveryType;
    private String deliverPhoneNumber;
    private String date;
    private String time;
    private double userLat;
    private double userLng;
    private String userAddress;



    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUserLat() {
        return userLat;
    }

    public void decreasePrice(int decreasePrice) {
        this.price -= decreasePrice;
    }

    public void setUserLat(double userLat) {
        this.userLat = userLat;
    }

    public double getUserLng() {
        return userLng;
    }

    public void setUserLng(double userLng) {
        this.userLng = userLng;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<ClothNumberModel> getClothesModel() {
        return clothesModel;
    }

    public void setClothesModel(List<ClothNumberModel> clothesModel) {
        this.clothesModel = clothesModel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDeliverAvatar() {
        return deliverAvatar;
    }

    public void setDeliverAvatar(String deliverAvatar) {
        this.deliverAvatar = deliverAvatar;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price += price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }

    public int getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(int deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliverPhoneNumber() {
        return deliverPhoneNumber;
    }

    public void setDeliverPhoneNumber(String deliverPhoneNumber) {
        this.deliverPhoneNumber = deliverPhoneNumber;
    }



    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
