package com.example.tp3.data;

import com.example.tp3.R;
import com.example.tp3.models.Post;
import com.example.tp3.models.StoryHighlight;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataProvider {
    private static final List<Post> POSTS = new ArrayList<>();
    private static final List<StoryHighlight> HIGHLIGHTS = new ArrayList<>();

    private static final int[] PROFILE_IMAGES = {
            R.drawable.ic_person,
            R.drawable.ic_person,
            R.drawable.ic_person,
            R.drawable.ic_person,
            R.drawable.ic_person,
            R.drawable.ic_person,
            R.drawable.ic_person,
            R.drawable.ic_person,
            R.drawable.ic_person,
            R.drawable.ic_person
    };
    private static final int[] FEED_IMAGES = {
            R.drawable.room,
            R.drawable.coding,
            R.drawable.cat,
            R.drawable.relax,
            R.drawable.flowers,
            R.drawable.room,
            R.drawable.coding,
            R.drawable.cat,
            R.drawable.relax,
            R.drawable.flowers
    };
    private static final int[] HIGHLIGHT_IMAGES = {
            R.drawable.sunset,
            R.drawable.controller,
            R.drawable.sunset,
            R.drawable.controller,
            R.drawable.sunset,
            R.drawable.controller,
            R.drawable.sunset
    };

    static {
        // initialize 10 dummy feed posts
        for(int i = 0; i < 10; i++){
            POSTS.add(new Post(
                    UUID.randomUUID().toString(),
                    "user" + (i + 1),
                    PROFILE_IMAGES[i],  // Added profileImageUrl
                    FEED_IMAGES[i],  // This is postImageUrl
                    "Caption #" + (i + 1)
            ));
        }
        // initialize 7 dummy highlights
        for(int i = 0; i < HIGHLIGHT_IMAGES.length; i++){
            HIGHLIGHTS.add(new StoryHighlight(
                UUID.randomUUID().toString(),
                "Story " + (i + 1),
                HIGHLIGHT_IMAGES[i]
            ));
        }

        // initialize 5 dummy profile posts
        for (int i = 0; i < 5; i++) {
            POSTS.add(new Post(
                UUID.randomUUID().toString(),
                "cse_9124",
                R.drawable.gojo,  // Added profileImageUrl
                FEED_IMAGES[i],  // This is postImageUrl
                "Ini Caption Ke-" + (i + 1)
            ));
        }
    }

    public static List<Post> getFeedPosts() {
        return POSTS;
    }

    public static List<Post> getUserPosts(String username) {
        List<Post> userPosts = new ArrayList<>();
        for (Post post : POSTS) {
            if (post.getUsername().equals(username)) {
                userPosts.add(post);
            }
        }
        return userPosts;
    }

    public static List<Post> getProfilePosts() {
        List<Post> profilePosts = new ArrayList<>();
        for (Post post : POSTS) {
            if (post.getUsername().equals("cse_9124")) {
                profilePosts.add(post);
            }
        }
        return profilePosts;
    }

    public static List<StoryHighlight> getHighlights() {
        return HIGHLIGHTS;
    }

    public static void addPost(Post post) {
        POSTS.add(0, post);
    }
}