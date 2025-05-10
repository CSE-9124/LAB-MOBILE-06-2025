package com.example.tp5.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp5.adapters.BookAdapter;
import com.example.tp5.data.DataManager;
import com.example.tp5.R;
import com.example.tp5.models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private SearchView searchView;
    private Spinner genreSpinner;
    private List<Book> bookList = new ArrayList<>();
    private List<String> genreList;
    private ProgressBar progressBar;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        searchView = view.findViewById(R.id.search_view);
        genreSpinner = view.findViewById(R.id.genre_spinner);
        progressBar = view.findViewById(R.id.progress_bar);

        // Initialize executor and handler for background processing
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        setupRecyclerView();
        setupSearchView();
        setupGenreSpinner();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Update list in case favorites have changed
        loadBooks();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(getContext(), bookList);
        recyclerView.setAdapter(bookAdapter);

        loadBooks();
    }

    private void loadBooks() {
        // Hide books and show loading animation
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        executorService.execute(() -> {
            try {
                // Show loading animation for 2 seconds
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Background thread work
            final List<Book> loadedBooks = DataManager.getInstance().getAllBooks();

            // Update UI on main thread
            mainHandler.post(() -> {
                bookList = loadedBooks;
                bookAdapter.updateData(bookList);
                // Hide loading animation and show books
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            });
        });
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });
    }

    private void performSearch(String query) {
        // Hide books and show loading animation
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        executorService.execute(() -> {
            try {
                // Show loading animation for 2 seconds
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Background thread work
            final List<Book> searchResults;
            if (query.isEmpty()) {
                searchResults = DataManager.getInstance().getAllBooks();
            } else {
                searchResults = DataManager.getInstance().searchBooks(query);
            }

            // Update UI on main thread
            mainHandler.post(() -> {
                bookAdapter.updateData(searchResults);
                // Hide loading animation and show books
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            });
        });
    }

    private void setupGenreSpinner() {
        // Get all unique genres
        genreList = new ArrayList<>();
        genreList.add("All Genres");
        genreList.addAll(DataManager.getInstance().getAllGenres());

        // Create adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, genreList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(spinnerAdapter);

        // Set spinner listener
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterByGenre(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void filterByGenre(int position) {
        // Hide books and show loading animation
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        executorService.execute(() -> {
            try {
                // Show loading animation for 2 seconds
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Background thread work
            final List<Book> filteredBooks;
            if (position == 0) { // Assuming "All" is at position 0
                filteredBooks = DataManager.getInstance().getAllBooks();
            } else {
                String selectedGenre = genreList.get(position);
                filteredBooks = DataManager.getInstance().getBooksByGenre(selectedGenre);
            }

            // Update UI on main thread
            mainHandler.post(() -> {
                bookAdapter.updateData(filteredBooks);
                // Hide loading animation and show books
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            });
        });
    }
}