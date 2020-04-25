package com.example.sneakerreleasecountdown;

import java.util.Date;

public class Sneaker {

    private String name;
    private Date releaseDate;
    private int imageResource;

    public Sneaker(String name, Date releaseDate, int imageResource) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
