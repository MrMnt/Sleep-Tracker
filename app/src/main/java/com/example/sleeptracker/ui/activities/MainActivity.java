package com.example.sleeptracker.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sleeptracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// TODO: add Toast (or any other kind) indicator when user
//  Does something which would require a notification
//  (confirmation that a sleep was added to the DB for example)
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    
    private NavController navController;
    private BottomNavigationView bottomNav;

    // Temp bug "fix"..
    private Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = Toast.makeText(this, "You need to be logged in", Toast.LENGTH_SHORT);
        initBottomNav();
    }

    // Code for bottom navigation
    private void initBottomNav(){
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            // Temp bug "fix"..
            if(FirebaseAuth.getInstance().getCurrentUser() == null){
                t.show();
                return false;
            }

            switch(item.getItemId()){
                case R.id.add_sleep_data_page: {
                    navController.navigate(R.id.action_global_addSleepDataFragment);
                    return true;
                }
                case R.id.sleep_data_list_page: {
                    navController.navigate(R.id.action_global_sleepDataFragment);
                    return true;
                }
                case R.id.sleep_data_chart_page: {
                    navController.navigate(R.id.action_global_sleepDataChartFragment);
                    return true;
                }
            }
            return false;
        });
    }


    // --- Code for adding the menu and handling user sign out ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_sign_out:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signOut(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) mAuth.signOut();

        navController.navigate(R.id.action_global_authFragment);
    }

    // Not sure yet why NavController cannot be initialized in onCreate, but sure. This works
    @Override
    protected void onStart() {
        super.onStart();
        initNavController();
    }

    private void initNavController(){
        navController = Navigation.findNavController(this, R.id.fragment_container_view);

        // Make sure navigation is NOT visible when we are in the Auth fragment
        // TODO: making bottomNav invisible is not working..
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            Log.d(TAG, "initNavController: " + navDestination.getId() + "==" + R.id.action_global_authFragment);
            if(navDestination.getId() == R.id.action_global_authFragment){
                bottomNav.setVisibility(View.GONE);
            } else {
                bottomNav.setVisibility(View.VISIBLE);
            }
        });
    }
}