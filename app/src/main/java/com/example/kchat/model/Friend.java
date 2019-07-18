package com.example.kchat.model;

public class Friend {
    private String name;
    private int imageId;
    public Friend(String name, int imageId){
        this.imageId = imageId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}

