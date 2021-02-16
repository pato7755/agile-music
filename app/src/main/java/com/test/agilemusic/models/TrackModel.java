package com.test.agilemusic.models;

public class TrackModel {

    private String trackId;
    private String trackName;
    private String trackNumber;
    private boolean isStreamable;
    private String previewUrl;

    public TrackModel(String trackId, String trackName, String trackNumber, boolean isStreamable, String previewUrl) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.trackNumber = trackNumber;
        this.isStreamable = isStreamable;
        this.previewUrl = previewUrl;
    }


    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public boolean isStreamable() {
        return isStreamable;
    }

    public void setStreamable(boolean streamable) {
        isStreamable = streamable;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @Override
    public String toString() {
        return "SongModel{" +
                "trackId='" + trackId + '\'' +
                ", trackName='" + trackName + '\'' +
                ", trackNumber='" + trackNumber + '\'' +
                ", isStreamable=" + isStreamable +
                ", previewUrl='" + previewUrl + '\'' +
                '}';
    }
}
