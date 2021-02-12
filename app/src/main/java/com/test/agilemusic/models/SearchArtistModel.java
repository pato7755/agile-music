package com.test.agilemusic.models;

public class SearchArtistModel {

    private String artistId;
    private String artistName;
    private String genre;

    public SearchArtistModel(String artistId, String artistName, String genre) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.genre = genre;
    }

    public SearchArtistModel(String artistName, String genre) {
        this.artistName = artistName;
        this.genre = genre;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "SearchArtistModel{" +
                "artistId='" + artistId + '\'' +
                ", artistName='" + artistName + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
