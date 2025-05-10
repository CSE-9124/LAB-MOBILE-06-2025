package com.example.tp4.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tp4.data.DataManager;
import com.example.tp4.R;
import com.example.tp4.models.Book;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

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

    // Default cover image if user doesn't select one
    private int selectedCoverImageResourceId = R.drawable.book1;

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

        // Set current year as default
        yearInput.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));

        // Set up button listeners
        selectImageButton.setOnClickListener(v -> openGallery());
        addBookButton.setOnClickListener(v -> addNewBook());

        return view;
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // In a real app, save the image to app storage and store its path
                // For this example, we're just setting the URI to the ImageView
                coverImageView.setImageURI(selectedImageUri);

                // Randomly select a resource ID for simplicity
                // In a real app, you would save the actual image
                selectedCoverImageResourceId = getRandomCoverImageResourceId();
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

            // Create new book
            Book newBook = new Book(title, author, year, blurb, selectedCoverImageResourceId, genre, rating);

            // Add book to data manager
            DataManager.getInstance().addBook(newBook);

            // Show success message
            Toast.makeText(getContext(), "Book added successfully", Toast.LENGTH_SHORT).show();

            // Clear form fields
            clearFormFields();

            // Navigate to Home fragment to see the new book
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Year must be a number", Toast.LENGTH_SHORT).show();
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