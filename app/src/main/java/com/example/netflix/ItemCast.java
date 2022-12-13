package com.example.netflix;

public class ItemCast {
    private String sName;
    private String sImage;

    public ItemCast(String name, String image){
        sName=name;
        sImage=image;
    }

    public String getsName() {
        return sName;
    }

    public String getsImage() {
        return sImage;
    }
}
