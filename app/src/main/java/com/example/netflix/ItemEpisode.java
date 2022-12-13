package com.example.netflix;

public class ItemEpisode {
    private String sEpisode;
    private String sImageEpisode;
    private String sUrlEpisode;
    private String sSummary;

    public ItemEpisode(String episode, String imageEpisode, String urlEpisode, String summary){
        sEpisode=episode;
        sImageEpisode=imageEpisode;
        sUrlEpisode=urlEpisode;
        sSummary=summary;
    }

    public String getsEpisode() {
        return sEpisode;
    }

    public String getsImageEpisode() {
        return sImageEpisode;
    }

    public String getsUrlEpisode() {
        return sUrlEpisode;
    }

    public String getsSummary() {
        return sSummary;
    }
}
