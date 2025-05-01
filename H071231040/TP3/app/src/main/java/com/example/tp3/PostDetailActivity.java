package com.example.tp3;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tp3.data.DataProvider;
import com.example.tp3.models.Post;
import com.squareup.picasso.Picasso;

public class PostDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the post ID from the intent
        String id = getIntent().getStringExtra("postId");
        Post p = DataProvider.getFeedPosts()
                    .stream()
                    .filter(x->x.getId().equals(id))
                    .findFirst().orElse(null);
        
        if(p != null) {
            ((TextView)findViewById(R.id.detail_username)).setText(p.getUsername());
            ((TextView)findViewById(R.id.caption_username)).setText(p.getUsername());
            ((TextView)findViewById(R.id.detail_caption)).setText(p.getCaption());
            
            // Load profile image
            Picasso.get().load(p.getProfileImageUrl())
                    .into((ImageView)findViewById(R.id.profile_image));
            
            // Load post image
//            Picasso.get().load(p.getPostImageUrl())
//                    .into((ImageView)findViewById(R.id.detail_image));
            if (p.getPostImageUrl() instanceof Integer) {
                Picasso.get().load((Integer) p.getPostImageUrl()).into((ImageView)findViewById(R.id.detail_image));
            } else if (p.getPostImageUrl() instanceof Uri) {
                Picasso.get().load((Uri) p.getPostImageUrl()).into((ImageView)findViewById(R.id.detail_image));
            }
                    
            // Add back button functionality
            findViewById(R.id.back_button).setOnClickListener(v -> finish());
        }
    }
}