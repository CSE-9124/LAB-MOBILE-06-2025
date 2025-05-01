package com.example.tp3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tp3.data.DataProvider;
import com.example.tp3.models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.UUID;

public class PostUploadActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private Uri imageUri = null;
    private ImageView selectedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_upload);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        Button selectImageBtn = findViewById(R.id.select_image_button);
        selectedImageView = findViewById(R.id.selected_image);
        EditText captionEditText = findViewById(R.id.caption_edit_text);
        Button postButton = findViewById(R.id.post_button);
        ImageButton backButton = findViewById(R.id.back_button);

        // Set up back button
        backButton.setOnClickListener(v -> finish());

        // Set up image selection
        selectImageBtn.setOnClickListener(v -> {
            // Open image picker
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            startActivityForResult(intent, PICK_IMAGE);
        });
        
        // Set up post button
        postButton.setOnClickListener(v -> {
            String caption = captionEditText.getText().toString().trim();

            if (imageUri == null) {
                Toast.makeText(this, "Please select an Image First", Toast.LENGTH_SHORT).show();
                return;
            }

            Post newPost = new Post(
                    UUID.randomUUID().toString(),
                    "cse_9124",
                    R.drawable.gojo, // Profile image for cse_9124
                    imageUri, // Post image
                    caption
            );

            // Add post to the data provider
            DataProvider.addPost(newPost);

            Toast.makeText(this, "Post uploaded successfully!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            if (imageUri != null) {
                try {
                    // Take persistable permissions with BOTH flags
                    int takeFlags = data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;

                    getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } catch (SecurityException e) {
                    Toast.makeText(this,
                            "This image might not display after leaving the app",
                            Toast.LENGTH_SHORT).show();
                }

                selectedImageView.setVisibility(View.VISIBLE);
                selectedImageView.setImageURI(imageUri);
            }
        }
    }


}