package com.example.netflix;

public class ItemSeason {

    private String sSeason;
    private String sImageUrl;
    private String sUrlSeason;

    public ItemSeason(String imageUrl, String season, String urlSeason){
        sImageUrl=imageUrl;
        sSeason=season;
        sUrlSeason=urlSeason;
    }

    public String getsImageUrl(){
        return sImageUrl;
    }

    public String getsSeason(){
        return sSeason;
    }

    public String getsUrlSeason(){
        return sUrlSeason;
    }

}
