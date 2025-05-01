package com.example.tp3;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp3.adapters.PostAdapter;
import com.example.tp3.data.DataProvider;
import com.example.tp3.models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements PostAdapter.Listener {
    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_main);

//        RecyclerView stories = findViewById(R.id.stories_recycler_view);
//        stories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//        stories.setAdapter(new HighlightsAdapter(DataProvider.getHighlights(), this));

        RecyclerView rv = findViewById(R.id.home_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new PostAdapter(DataProvider.getFeedPosts(), this));

        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        nav.setSelectedItemId(R.id.nav_home);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
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
    public void onProfileClick(String username) {
        startActivity(new Intent(this, ProfileDetailActivity.class)
        .putExtra("username", username));
    }

    @Override
    public void onPostClick(Post p) {
        startActivity(new Intent(this, PostDetailActivity.class)
        .putExtra("postId", p.getId()));
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the RecyclerView with the latest posts
        RecyclerView rv = findViewById(R.id.home_recycler_view);
        rv.setAdapter(new PostAdapter(DataProvider.getFeedPosts(), this));
    }

//    @Override
//    public void onHighlightClick(StoryHighlight s) {
//        startActivity(new Intent(this, StoryDetailActivity.class)
//        .putExtra("highlightId", s.getId()));
//    }
}