package com.xbbxsnia.the2806.CaptainBike.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Order {
    private double[] sourceLocation;
    private int finalPrice;
    private boolean isDiscountEntered;
    private boolean isStopInWayChecked;
    private boolean isGoBackToSourceChecked;
    private String driverCode;
    private String driverCar;
    private String driverCarPlateNumber;
    private int distanceTime;
    private String driverName;
    private float driverRate;
    private String driverPhoneNumber;
    private List<Destination> destinations;

    public double[] getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(double[] sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public boolean isDiscountEntered() {
        return isDiscountEntered;
    }

    public void setDiscountEntered(boolean discountEntered) {
        isDiscountEntered = discountEntered;
    }

    public boolean isStopInWayChecked() {
        return isStopInWayChecked;
    }

    public void setStopInWayChecked(boolean stopInWayChecked) {
        isStopInWayChecked = stopInWayChecked;
    }

    public boolean isGoBackToSourceChecked() {
        return isGoBackToSourceChecked;
    }

    public void setGoBackToSourceChecked(boolean goBackToSourceChecked) {
        isGoBackToSourceChecked = goBackToSourceChecked;
    }

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    public String getDriverCar() {
        return driverCar;
    }

    public void setDriverCar(String driverCar) {
        this.driverCar = driverCar;
    }

    public String getDriverCarPlateNumber() {
        return driverCarPlateNumber;
    }

    public void setDriverCarPlateNumber(String driverCarPlateNumber) {
        this.driverCarPlateNumber = driverCarPlateNumber;
    }

    public int getDistanceTime() {
        return distanceTime;
    }

    public void setDistanceTime(int distanceTime) {
        this.distanceTime = distanceTime;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public float getDriverRate() {
        return driverRate;
    }

    public void setDriverRate(float driverRate) {
        this.driverRate = driverRate;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }
}
