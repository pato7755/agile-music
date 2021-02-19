package com.test.agilemusic.models;

public class VideosModel {

    private String trackName;
    private String thumbnalUrl;
    private String previewUrl;

    public VideosModel(String trackName, String thumbnalUrl, String previewUrl) {
        this.trackName = trackName;
        this.thumbnalUrl = thumbnalUrl;
        this.previewUrl = previewUrl;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getThumbnalUrl() {
        return thumbnalUrl;
    }

    public void setThumbnalUrl(String thumbnalUrl) {
        this.thumbnalUrl = thumbnalUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @Override
    public String toString() {
        return "VideosModel{" +
                "trackName='" + trackName + '\'' +
                ", thumbnalUrl='" + thumbnalUrl + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                '}';
    }
}
