package com.example.netflix;

public class Item {

    private String mImageUrl;
    private String mSerie;
    private String mUrlSeason;

    public Item(String imageUrl, String serie, String urlSeason){
        mImageUrl=imageUrl;
        mSerie=serie;
        mUrlSeason=urlSeason;
    }

    public String getmImageUrl(){
        return mImageUrl;
    }

    public String getmSerie(){
        return mSerie;
    }

    public String getmUrlSeason(){
        return mUrlSeason;
    }

}
