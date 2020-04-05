package com.lsc.material.appbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.lsc.material.R;

public class AppBarActivity extends AppCompatActivity {

    private MaterialToolbar mTopAppBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar4);

        mTopAppBar = findViewById(R.id.topAppBar);
        mTopAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.favorite:
                        Toast.makeText(AppBarActivity.this,"favorite",Toast.LENGTH_LONG).show();
                        //TODO
                        return true;
                    case R.id.more:
                        //TODO
                        return true;
                    case R.id.search:
                        //TODO
                        return true;
                }
                return false;
            }
        });
    }
}
