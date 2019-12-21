package com.william.gpsdata.dto;

import java.sql.Date;

public class DataDto {

    private Integer id;
    private Date date;
    private char nOrS;
    private double latitude;
    private char wOrE;
    private double longitude;
    private String userName;

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public char getnOrS() {
        return nOrS;
    }

    public void setnOrS(char nOrS) {
        this.nOrS = nOrS;
    }

    public char getwOrE() {
        return wOrE;
    }

    public void setwOrE(char wOrE) {
        this.wOrE = wOrE;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return this.getClass().getName() +
                "{id:" + this.id +
                ",date:" + this.date +
                ",nOrS:" + this.nOrS +
                ",latitude:" + this.latitude +
                ",wOrE:" + this.wOrE +
                ",longitude:" + this.longitude +
                ",user:" + this.userName +
                "}";
    }
}
