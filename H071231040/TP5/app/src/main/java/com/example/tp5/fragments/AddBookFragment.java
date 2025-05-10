package com.example.tp5.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tp5.data.DataManager;
import com.example.tp5.R;
import com.example.tp5.models.Book;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddBookFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView coverImageView;
    private TextInputEditText titleInput;
    private TextInputEditText authorInput;
    private TextInputEditText yearInput;
    private TextInputEditText genreInput;
    private TextInputEditText blurbInput;
    private RatingBar ratingBar;
    private Button selectImageButton;
    private Button addBookButton;
    private ProgressBar progressBar;

    // Default cover image if user doesn't select one
    private int selectedCoverImageResourceId = R.drawable.book1;
    // Keep track if image is custom or default
    private boolean isCustomImage = false;
    // Uri to the selected image
    private Uri selectedImageUri = null;

    private ExecutorService executorService;
    private Handler mainHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        // Initialize views
        coverImageView = view.findViewById(R.id.cover_image);
        titleInput = view.findViewById(R.id.title_input);
        authorInput = view.findViewById(R.id.author_input);
        yearInput = view.findViewById(R.id.year_input);
        genreInput = view.findViewById(R.id.genre_input);
        blurbInput = view.findViewById(R.id.blurb_input);
        ratingBar = view.findViewById(R.id.rating_bar);
        selectImageButton = view.findViewById(R.id.select_image_button);
        addBookButton = view.findViewById(R.id.add_book_button);
        progressBar = view.findViewById(R.id.progress_bar);

        // Initialize executor and handler for background processing
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        // Set current year as default
        yearInput.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));

        // Set up button listeners
        selectImageButton.setOnClickListener(v -> openGallery());
        addBookButton.setOnClickListener(v -> addNewBook());

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // Show the selected image in the ImageView
                coverImageView.setImageURI(selectedImageUri);
                isCustomImage = true;
            }
        }
    }

    private int getRandomCoverImageResourceId() {
        // For simplicity, just cycling through resource IDs
        int[] resourceIds = {R.drawable.book1, R.drawable.book2, R.drawable.book3,
                R.drawable.book4, R.drawable.book5};
        return resourceIds[(int) (Math.random() * resourceIds.length)];
    }

    private void addNewBook() {
        // Validate input fields
        String title = titleInput.getText().toString().trim();
        String author = authorInput.getText().toString().trim();
        String yearStr = yearInput.getText().toString().trim();
        String genre = genreInput.getText().toString().trim();
        String blurb = blurbInput.getText().toString().trim();
        float rating = ratingBar.getRating();

        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty() ||
                genre.isEmpty() || blurb.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int year = Integer.parseInt(yearStr);
            if (year < 0 || year > Calendar.getInstance().get(Calendar.YEAR)) {
                Toast.makeText(getContext(), "Year must be a valid number", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show loading indicator
            progressBar.setVisibility(View.VISIBLE);
            addBookButton.setEnabled(false);

            executorService.execute(() -> {
                try {
                    // Sleep to simulate processing time
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            
                // Create new book with appropriate image source
                final Book newBook;
            
                if (isCustomImage && selectedImageUri != null) {
                    // Save the image and get path
                    String imagePath = saveImageToInternalStorage(selectedImageUri);
                    
                    if (imagePath != null) {
                        // Create book with custom image path
                        newBook = new Book(title, author, year, blurb, imagePath, genre, rating);
                    } else {
                        // Fallback to resource if saving fails
                        newBook = new Book(title, author, year, blurb, selectedCoverImageResourceId, genre, rating);
                    }
                } else {
                    // Create book with resource image
                    newBook = new Book(title, author, year, blurb, selectedCoverImageResourceId, genre, rating);
                }
            
                // Add book to data manager
                DataManager.getInstance().addBook(newBook);

                // Update UI on main thread
                mainHandler.post(() -> {
                    // Hide loading indicator
                    progressBar.setVisibility(View.GONE);
                    addBookButton.setEnabled(true);

                    // Show success message
                    Toast.makeText(getContext(), "Book added successfully", Toast.LENGTH_SHORT).show();

                    // Clear form fields
                    clearFormFields();

                    // Navigate to Home fragment to see the new book
                    if (getActivity() != null) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new HomeFragment())
                                .commit();
                    }
                });
            });

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Year must be a number", Toast.LENGTH_SHORT).show();
        }
    }

    private String saveImageToInternalStorage(Uri imageUri) {
        try {
            // Get bitmap from URI
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
            requireActivity().getContentResolver(), imageUri);

            // Generate a unique filename
            String filename = "book_cover_" + UUID.randomUUID().toString() + ".jpg";

            // Get app's internal directory
            File directory = requireContext().getFilesDir();
            File file = new File(directory, filename);

            // Save bitmap to file
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();

            return file.getAbsolutePath(); // Return the file path

        } catch (IOException e) {
            e.printStackTrace();
            return null; // Default image if saving fails
        }
    }

    private void clearFormFields() {
        titleInput.setText("");
        authorInput.setText("");
        yearInput.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        genreInput.setText("");
        blurbInput.setText("");
        ratingBar.setRating(0f);
        coverImageView.setImageResource(R.drawable.book1);
        selectedCoverImageResourceId = R.drawable.book1;
    }
}