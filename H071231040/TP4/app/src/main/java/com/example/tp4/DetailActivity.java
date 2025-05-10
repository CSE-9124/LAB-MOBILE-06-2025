package com.example.tp4;


import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tp4.data.DataManager;
import com.example.tp4.models.Book;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class DetailActivity extends AppCompatActivity {

    private Book book;
    private ImageButton favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getIntent() != null && getIntent().hasExtra("book")) {
            book = (Book) getIntent().getSerializableExtra("book");
            if (book != null) {
                initializeViews();
                displayBookData();
            } else {
                Toast.makeText(this, "Invalid book data", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "No book data received", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) toolbar.getParent();
        collapsingToolbarLayout.setTitle(book.getTitle());
    }

    private void displayBookData() {
        ImageView coverImageView = findViewById(R.id.detail_book_cover);
        TextView titleTextView = findViewById(R.id.detail_book_title);
        TextView authorTextView = findViewById(R.id.detail_book_author);
        TextView yearTextView = findViewById(R.id.detail_book_year);
        TextView genreTextView = findViewById(R.id.detail_book_genre);
        TextView blurbTextView = findViewById(R.id.detail_book_blurb);
        RatingBar ratingBar = findViewById(R.id.detail_book_rating);
        favoriteButton = findViewById(R.id.favorite_button);

        coverImageView.setImageResource(book.getCoverImageResourceId());
        titleTextView.setText(book.getTitle());
        authorTextView.setText(book.getAuthor());
        yearTextView.setText(String.valueOf(book.getPublishYear()));
        genreTextView.setText(book.getGenre());
        blurbTextView.setText(book.getBlurb());
        ratingBar.setRating(book.getRating());

        updateFavoriteIcon();
        setupFavoriteButton();
    }

    private void setupFavoriteButton() {
        favoriteButton.setOnClickListener(v -> {
            // Toggle favorite status
            book.setFavorite(!book.isFavorite());

            // Update the book in DataManager to ensure it appears in FavoritesFragment
            DataManager.getInstance().updateBookFavoriteStatus(book);

            // Update UI
            updateFavoriteIcon();

            // Show feedback to user
            String message = book.isFavorite() ? getString(R.string.added_to_favorites) : getString(R.string.removed_from_favorites);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    private void updateFavoriteIcon() {
        favoriteButton.setImageResource(book.isFavorite() ?
                android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
