package com.example.kchat.model;

public class Converse {
    private int imageId;
    private String name;
    private String message;
    private String time;
    private long time1;
    public Converse(String name, String message, String time, int imageId){
        this.imageId = imageId;
        this.message = message;
        this.name = name;
        this.time = time;
    }
    public Converse(String name, String message, long time, int imageId){
        this.imageId = imageId;
        this.message = message;
        this.name = name;
        this.time1 = time;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }
}
