package com.lsc.material.bottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lsc.material.R;

public class BottomNavigationActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.bn_first:
                        Toast.makeText(getApplicationContext(),"first",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.bn_second:
                        Toast.makeText(getApplicationContext(),"second",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.bn_third:
                        Toast.makeText(getApplicationContext(),"third",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.bn_four:
                        Toast.makeText(getApplicationContext(),"four",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.bn_five:
                        Toast.makeText(getApplicationContext(),"five",Toast.LENGTH_LONG).show();
                        return true;
                }

                return false;
            }
        });
    }
}
