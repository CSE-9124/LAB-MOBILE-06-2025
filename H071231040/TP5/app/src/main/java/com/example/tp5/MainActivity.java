package com.example.tp5;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tp5.fragments.AddBookFragment;
import com.example.tp5.fragments.FavoritesFragment;
import com.example.tp5.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(this);

        // Menyusun fragment default hanya jika tidak ada savedInstanceState
        if (savedInstanceState == null) {
            // Menetapkan HomeFragment sebagai fragment default
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        int itemId = item.getItemId();

        if (itemId == R.id.navigation_home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.navigation_add_book) {
            selectedFragment = new AddBookFragment();
        } else if (itemId == R.id.navigation_favorites) {
            selectedFragment = new FavoritesFragment();
        }

        if (selectedFragment != null) {
            // Menghindari penggantian fragment yang sama jika sudah ditampilkan
            if (!selectedFragment.getClass().getName().equals(getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getName())) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .addToBackStack(null) // Menambahkan transaksi fragment ke back stack untuk navigasi sejarah
                        .commit();
            }
            return true;
        }
        return false;
    }
}
