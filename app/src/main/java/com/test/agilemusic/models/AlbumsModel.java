package com.test.agilemusic.models;

import android.graphics.Bitmap;

public class AlbumsModel {

    private String albumId;
    private String albumName;
    private String yearOfRelease;
    private String price;
    private String numberOfTracks;
    private String currency;
    private String imageUrl;

    public AlbumsModel(String albumId, String albumName, String yearOfRelease, String price, String numberOfTracks, String currency, String imageUrl) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.yearOfRelease = yearOfRelease;
        this.price = price;
        this.numberOfTracks = numberOfTracks;
        this.currency = currency;
        this.imageUrl = imageUrl;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(String yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumberOfTracks() {
        return numberOfTracks;
    }

    public void setNumberOfTracks(String numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "AlbumsModel{" +
                "albumId='" + albumId + '\'' +
                ", albumName='" + albumName + '\'' +
                ", yearOfRelease='" + yearOfRelease + '\'' +
                ", price='" + price + '\'' +
                ", numberOfTracks='" + numberOfTracks + '\'' +
                ", currency='" + currency + '\'' +
                ", imageUrl='" + imageUrl +
                '}';
    }
}
