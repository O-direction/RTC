package com.example.kchat.model;

public class Information {
    private String field;
    private String info;
    private int icon;
    public Information(String field, String info, int icon){
        this.field = field;
        this.icon = icon;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public String getField() {
        return field;
    }

    public int getIcon() {
        return icon;
    }
}
