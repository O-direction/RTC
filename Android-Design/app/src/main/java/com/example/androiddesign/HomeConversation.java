package com.example.androiddesign;

public class HomeConversation {
    private int icon;
    private String name;
    private String message;

    public HomeConversation(){

    }
    public HomeConversation(int icon, String name, String message){
        this.icon = icon;
        this.name = name;
        this.message = message;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
