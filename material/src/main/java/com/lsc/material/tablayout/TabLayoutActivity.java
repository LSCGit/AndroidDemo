package com.lsc.material.tablayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.lsc.material.R;

public class TabLayoutActivity extends AppCompatActivity {

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);

        mTabLayout = findViewById(R.id.tabs);
        BadgeDrawable badge = mTabLayout.getTabAt(0).getOrCreateBadge();
        badge.setVisible(true);
        badge.setNumber(99);

        //去掉badge
        //mTabLayout.getTabAt(0).removeBadge();
    }


}
