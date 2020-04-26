package com.example.sneakerreleasecountdown;

import java.util.Date;

class Sneaker {

    private String name;
    private Date releaseDate;
    private int imageResource;
    private boolean isFavorite;
    private int sneakerID;

    public Sneaker(String name, Date releaseDate, int imageResource, boolean isFavorite, int sneakerID) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.imageResource = imageResource;
        this.isFavorite = isFavorite;
        this.sneakerID = sneakerID;
    }

    public String getName() {
        return name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getSneakerID() {
        return sneakerID;
    }

    public void setSneakerID(int sneakerID) {
        this.sneakerID = sneakerID;
    }
}
