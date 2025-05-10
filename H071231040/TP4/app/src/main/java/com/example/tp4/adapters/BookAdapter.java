package com.example.tp4.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp4.DetailActivity;
import com.example.tp4.R;
import com.example.tp4.models.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.coverImageView.setImageResource(book.getCoverImageResourceId());
        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(book.getAuthor());
        holder.yearTextView.setText(String.valueOf(book.getPublishYear()));
        holder.genreTextView.setText(book.getGenre());
        holder.ratingBar.setRating(book.getRating());

        holder.favoriteIcon.setImageResource(book.isFavorite() ?
                android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);

        holder.itemView.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(context, DetailActivity.class);
                if (!(context instanceof Activity)) {
                    context = holder.itemView.getContext();
                }
                intent.putExtra("book", book);
                context.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(context, "Error opening book details", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

        holder.favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setFavorite(!book.isFavorite());
                holder.favoriteIcon.setImageResource(book.isFavorite() ?
                        android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void updateData(List<Book> newBooks) {
        bookList = newBooks;
        notifyDataSetChanged();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImageView;
        TextView titleTextView;
        TextView authorTextView;
        TextView yearTextView;
        TextView genreTextView;
        RatingBar ratingBar;
        ImageView favoriteIcon;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.book_cover);
            titleTextView = itemView.findViewById(R.id.book_title);
            authorTextView = itemView.findViewById(R.id.book_author);
            yearTextView = itemView.findViewById(R.id.book_year);
            genreTextView = itemView.findViewById(R.id.book_genre);
            ratingBar = itemView.findViewById(R.id.book_rating);
            favoriteIcon = itemView.findViewById(R.id.favorite_icon);
        }
    }
}
