package vn.edu.fpt.calotracker;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private TextView textViewCaloriesConsumed;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
//        Button button = findViewById(R.id.buttonUpdateCalories);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_calculation) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new CalculationFragment())
                            .commit();
                    return true;
                } else if (id == R.id.menu_setting) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new SettingFragment())
                            .commit();
                    return true;
                } else if (id == R.id.menu_tracker) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new TrackerFragment())
                            .commit();
                    return true;
                }
                return false;
            }
    });
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new TrackerFragment())
                    .commit();
    }
    }

