package com.example.tp3;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp3.adapters.HighlightsAdapter;
import com.example.tp3.adapters.ProfilePostsAdapter;
import com.example.tp3.data.DataProvider;
import com.example.tp3.models.Post;
import com.example.tp3.models.StoryHighlight;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // RecyclerView for highlights
        RecyclerView hlRv = findViewById(R.id.highlights_recycler_view);
        hlRv.setLayoutManager(
            new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        );

        hlRv.setAdapter(new HighlightsAdapter(
                DataProvider.getHighlights(), new HighlightsAdapter.Listener() {
                    @Override
                    public void onHighlightClick(StoryHighlight s) {
                        startActivity(new Intent(ProfileActivity.this, StoryDetailActivity.class)
                                .putExtra("highlightId", s.getId()));
                    }
                }
        ));

        // RecyclerView for posts
        RecyclerView grid = findViewById(R.id.posts_recycler_view);
        grid.setLayoutManager(
                new GridLayoutManager(this, 3)
        );

        grid.setAdapter(new ProfilePostsAdapter(
            DataProvider.getProfilePosts(), new ProfilePostsAdapter.Listener() {
                public void onPostClick(Post p){
                    startActivity(new Intent(ProfileActivity.this, PostDetailActivity.class)
                        .putExtra("postId", p.getId()));
                }
            }
        ));

        // Bottom Navigation
        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        nav.setSelectedItemId(R.id.nav_profile);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            }
            if (id == R.id.nav_upload) {
                startActivity(new Intent(this, PostUploadActivity.class));
                return true;
            }
            return true;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the RecyclerView with the latest posts
        RecyclerView grid = findViewById(R.id.posts_recycler_view);
        grid.setAdapter(new ProfilePostsAdapter(
            DataProvider.getProfilePosts(), new ProfilePostsAdapter.Listener() {
                public void onPostClick(Post p){
                    startActivity(new Intent(ProfileActivity.this, PostDetailActivity.class)
                        .putExtra("postId", p.getId()));
                }
            }
        ));
    }
}