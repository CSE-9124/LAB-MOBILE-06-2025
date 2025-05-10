package com.example.tp4.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp4.adapters.BookAdapter;
import com.example.tp4.data.DataManager;
import com.example.tp4.R;
import com.example.tp4.models.Book;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private SearchView searchView;
    private Spinner genreSpinner;
    private List<Book> bookList = new ArrayList<>();
    private List<String> genreList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        searchView = view.findViewById(R.id.search_view);
        genreSpinner = view.findViewById(R.id.genre_spinner);

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

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(getContext(), bookList);
        recyclerView.setAdapter(bookAdapter);

        loadBooks();
    }

    private void loadBooks() {
        bookList = DataManager.getInstance().getAllBooks();
        bookAdapter.updateData(bookList);
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
        if(query.isEmpty()) {
            loadBooks();
        } else {
            List<Book> searchResults = DataManager.getInstance().searchBooks(query);
            bookAdapter.updateData(searchResults);
        }
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
        if (position == 0) {
            // "All Genres" selected
            loadBooks();
        } else {
            String selectedGenre = genreList.get(position);
            List<Book> filteredBooks = DataManager.getInstance().getBooksByGenre(selectedGenre);
            bookAdapter.updateData(filteredBooks);
        }
    }
}