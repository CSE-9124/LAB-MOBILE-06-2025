package com.example.tp4.models;

import java.io.Serializable;
import java.util.UUID;

public class Book implements Serializable {
    private String id;
    private String title;
    private String author;
    private int publishYear;
    private String blurb;
    private int coverImageResourceId;
    private boolean isFavorite;
    private String genre;
    private float rating;

    public Book(String title, String author, int publishYear, String blurb, int coverImageResourceId, String genre, float rating) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.blurb = blurb;
        this.coverImageResourceId = coverImageResourceId;
        this.isFavorite = false;
        this.genre = genre;
        this.rating = rating;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public int getCoverImageResourceId() {
        return coverImageResourceId;
    }

    public void setCoverImageResourceId(int coverImageResourceId) {
        this.coverImageResourceId = coverImageResourceId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
