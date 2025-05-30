package com.example.clinicreceptionworkstation.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.clinicreceptionworkstation.fragments.GuideFragment;
import com.example.clinicreceptionworkstation.fragments.JournalFragment;
import com.example.clinicreceptionworkstation.fragments.OverviewFragment;
import com.example.clinicreceptionworkstation.R;
import com.example.clinicreceptionworkstation.fragments.ScheduleFragment;
import com.example.clinicreceptionworkstation.fragments.StatisticsFragment;
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