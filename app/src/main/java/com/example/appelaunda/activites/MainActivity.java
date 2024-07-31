package com.example.appelaunda.activites;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager; // Import FragmentManager
import androidx.fragment.app.FragmentTransaction;

import com.example.appelaunda.R;
import com.example.appelaunda.fragments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth; // Remove unused import
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String HOME_FRAGMENT_TAG = "home_fragment"; // Tag for fragment transactions

    private FragmentManager fragmentManager; // Use FragmentManager
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // System bar insets handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        // Improved Toolbar setup
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_person_24);
        }

        fragmentManager = getSupportFragmentManager(); // Initialize FragmentManager
        loadHomeFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) { // Use '==' for comparison
            auth.signOut();
            startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            finish();
        } else if (id == R.id.menu_my_cart) { // Use '==' for comparison
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        }
        return true;
    }

    private void loadHomeFragment() {
        // Check if fragment already exists
        HomeFragment homeFragment = (HomeFragment) fragmentManager.findFragmentByTag(HOME_FRAGMENT_TAG);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home_container, homeFragment, HOME_FRAGMENT_TAG);
        transaction.commit();
    }

    // Remove unused method
    // private void setSupportActionBar(Toolbar toolbar) {}
}