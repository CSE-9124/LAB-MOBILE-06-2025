package com.example.tp3.models;

public class StoryHighlight {
    private String id;
    private String title;
    private Integer imageUrl;

    public StoryHighlight(String id, String title, Integer imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

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

    public Integer getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }
}
