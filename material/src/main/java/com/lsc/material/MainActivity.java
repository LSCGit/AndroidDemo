package com.lsc.material;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.lsc.material.appbar.AppBarActivity;
import com.lsc.material.bottomnavigation.BottomNavigationActivity;
import com.lsc.material.bottomsheet.BottomSheetsActivity;
import com.lsc.material.button.ButtonActivity;
import com.lsc.material.card.CardActivity;
import com.lsc.material.chips.ChipsActivity;
import com.lsc.material.fab.FABActivity;
import com.lsc.material.tablayout.TabLayoutActivity;
import com.lsc.material.textfield.TextFieldActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        Button appBarActivity = new Button(this);
        appBarActivity.setText("AppBar");
        appBarActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AppBarActivity.class));
            }
        });
        appBarActivity.setLayoutParams(layoutParams);

        Button bottomNavigationActivity = new Button(this);
        bottomNavigationActivity.setText("BottomNavigation");
        bottomNavigationActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
            }
        });
        bottomNavigationActivity.setLayoutParams(layoutParams);

        Button sheetsActivity = new Button(this);
        sheetsActivity.setText("BottomSheet");
        sheetsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BottomSheetsActivity.class));
            }
        });
        sheetsActivity.setLayoutParams(layoutParams);

        Button buttonActivity = new Button(this);
        buttonActivity.setText("buttonActivity");
        buttonActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ButtonActivity.class));
            }
        });
        buttonActivity.setLayoutParams(layoutParams);

        Button cardActivity = new Button(this);
        cardActivity.setText("cardActivity");
        cardActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CardActivity.class));
            }
        });
        cardActivity.setLayoutParams(layoutParams);

        Button chipsActivity = new Button(this);
        chipsActivity.setText("chipsActivity");
        chipsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChipsActivity.class));
            }
        });
        chipsActivity.setLayoutParams(layoutParams);

        Button fabActivity = new Button(this);
        fabActivity.setText("fabActivity");
        fabActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FABActivity.class));
            }
        });
        fabActivity.setLayoutParams(layoutParams);

        Button tabLayoutActivity = new Button(this);
        tabLayoutActivity.setText("tabLayoutActivity");
        tabLayoutActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TabLayoutActivity.class));
            }
        });
        tabLayoutActivity.setLayoutParams(layoutParams);

        Button textActivity = new Button(this);
        textActivity.setText("textActivity");
        textActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TextFieldActivity.class));
            }
        });
        textActivity.setLayoutParams(layoutParams);

        LayoutInflater inflater = getLayoutInflater();
        LinearLayout view = (LinearLayout)inflater.inflate(R.layout.activity_main,null);
        view.setOrientation(LinearLayout.VERTICAL);
        view.addView(appBarActivity);
        view.addView(bottomNavigationActivity);
        view.addView(sheetsActivity);
        view.addView(buttonActivity);
        view.addView(cardActivity);
        view.addView(chipsActivity);
        view.addView(fabActivity);
        view.addView(tabLayoutActivity);
        view.addView(textActivity);

        setContentView(view);
        initSnackbar(view);
    }

    private void initSnackbar(View view){
        Snackbar.make(view, "是否关闭", Snackbar.LENGTH_LONG)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("lsc", "onClick: " );
                    }
                }).show();
    }
}
