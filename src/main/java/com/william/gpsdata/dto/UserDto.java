package com.william.gpsdata.dto;

public class UserDto {

    private Integer id;
    private String name;
    private String info;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return this.getClass().getName() +
                "{id:" + this.id +
                ",name:" + this.name +
                ",info:" + this.info +
                "}";
    }
}