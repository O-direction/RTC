package com.example.androiddesign;

public class Friends {
    private int icon;
    private String name;

    public Friends(){

    }

    public Friends(int icon, String name){
        this.icon = icon;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getIcon(){
        return icon;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setIcon(int icon){
        this.icon = icon;
    }
}
