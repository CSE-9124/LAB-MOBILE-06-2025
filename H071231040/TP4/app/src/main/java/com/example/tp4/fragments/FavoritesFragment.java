package com.example.tp4.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp4.adapters.BookAdapter;
import com.example.tp4.data.DataManager;
import com.example.tp4.R;
import com.example.tp4.models.Book;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private TextView emptyView;
    private List<Book> favoriteBooks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.favorites_recycler_view);
        emptyView = view.findViewById(R.id.empty_view);

        setupRecyclerView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Update list when fragment becomes visible
        loadFavoriteBooks();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookAdapter = new BookAdapter(getContext(), favoriteBooks);
        recyclerView.setAdapter(bookAdapter);

        loadFavoriteBooks();
    }

    private void loadFavoriteBooks() {
        favoriteBooks = DataManager.getInstance().getFavoriteBooks();
        bookAdapter.updateData(favoriteBooks);

        // Show empty view if no favorites
        if (favoriteBooks.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }
}