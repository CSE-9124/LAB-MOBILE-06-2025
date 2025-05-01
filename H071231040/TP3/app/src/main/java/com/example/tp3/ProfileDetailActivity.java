package com.example.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp3.adapters.ProfilePostsAdapter;
import com.example.tp3.data.DataProvider;
import com.example.tp3.models.Post;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get username from intent
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            // Set the username in the TextView
            ((TextView) findViewById(R.id.username)).setText(username);
        }

        // Set Profile info
        ((TextView) findViewById(R.id.username)).setText(username);
        ((TextView) findViewById(R.id.real_name)).setText(username.toUpperCase());
        ((TextView) findViewById(R.id.bio)).setText("This is a bio for " + username);

        ((TextView) findViewById(R.id.post_count)).setText("42");
        ((TextView) findViewById(R.id.followers_count)).setText("10.5K");
        ((TextView) findViewById(R.id.following_count)).setText("523");

        // Set profile image - you might want logic to pick different images based on username
        CircleImageView profileImage = findViewById(R.id.profile_image);
        profileImage.setImageResource(R.drawable.ic_person);

        // Setup Follow button
        Button followButton = findViewById(R.id.follow_button);
        followButton.setOnClickListener(v -> {
            if (followButton.getText().toString().equals("Follow")) {
                followButton.setText("Following");
                followButton.setBackgroundColor(getColor(R.color.dark_gray));
            } else {
                followButton.setText("Follow");
                followButton.setBackgroundColor(getColor(R.color.blue));
            }
        });

        // Setup back button
        findViewById(R.id.back_button).setOnClickListener(v -> finish());

        // Setup posts grid
        RecyclerView postsRv = findViewById(R.id.posts_recycler_view);
        postsRv.setLayoutManager(new GridLayoutManager(this, 3));

        postsRv.setAdapter(new ProfilePostsAdapter(
                DataProvider.getUserPosts(username), new ProfilePostsAdapter.Listener() {
                    @Override
                    public void onPostClick(Post p) {
                        startActivity(new Intent(ProfileDetailActivity.this, PostDetailActivity.class)
                                .putExtra("postId", p.getId()));
                    }
                }
        ));
    }
}