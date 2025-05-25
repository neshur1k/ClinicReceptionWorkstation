package com.example.clinicreceptionworkstation;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.overview_nav) {
                selectedFragment = new OverviewFragment();
            } else if (itemId == R.id.journal_nav) {
                selectedFragment = new JournalFragment();
            } else if (itemId == R.id.schedule_nav) {
                selectedFragment = new ScheduleFragment();
            } else if (itemId == R.id.guide_nav) {
                selectedFragment = new GuideFragment();
            } else if (itemId == R.id.statistics_nav) {
                selectedFragment = new StatisticsFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.schedule_nav);
        }
    }
}
