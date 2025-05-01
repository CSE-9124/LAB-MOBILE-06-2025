package com.example.tp3;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tp3.data.DataProvider;
import com.example.tp3.models.StoryHighlight;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_story_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the highlight ID from intent
        String highlightId = getIntent().getStringExtra("highlightId");

        if (highlightId != null) {
            // Find the highlight with this ID
            StoryHighlight highlight = null;
            for (StoryHighlight h : DataProvider.getHighlights()) {
                if (h.getId().equals(highlightId)) {
                    highlight = h;
                    break;
                }
            }

            if (highlight != null) {
                // Set the story content
                ImageView highlightImage = findViewById(R.id.highlight_image);
                TextView titleView = findViewById(R.id.story_title);
                ImageView imageView = findViewById(R.id.story_image);

                highlightImage.setImageResource(highlight.getImageUrl());

                titleView.setText(highlight.getTitle());

                imageView.setImageResource(highlight.getImageUrl());
            }
        }

        // Setup back button
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }
}