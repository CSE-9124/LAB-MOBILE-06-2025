package com.example.tp3.models;

import android.net.Uri;

public class Post {
    private String id;
    private String username;
    private Integer profileImageUrl;
    private Object postImageUrl; // Can be Integer or Uri
    private String caption;

    // Constructor for resource IDs (used by DataProvider)
    public Post(String id, String username, Integer profileImageUrl, Integer postImageUrl, String caption) {
        this.id = id;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.postImageUrl = postImageUrl;
        this.caption = caption;
    }

    // Constructor for URIs (used by PostUploadActivity)
    public Post(String id, String username, Integer profileImageUrl, Uri postImageUrl, String caption) {
        this.id = id;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.postImageUrl = postImageUrl;
        this.caption = caption;
    }

    public String getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public Integer getProfileImageUrl() {
        return profileImageUrl;
    }
    public Object getPostImageUrl() {
        return postImageUrl;
    }
    public String getCaption() {
        return caption;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setProfileImageUrl(Integer profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    public void setPostImageUrl(Integer postImageUrl) {
        this.postImageUrl = postImageUrl;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
}
