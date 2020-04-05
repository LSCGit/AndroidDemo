package com.lsc.material.card;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.lsc.material.R;

public class CardActivity extends AppCompatActivity {


    private MaterialCardView mCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card3);

        mCardView = findViewById(R.id.card);
        mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mCardView.setChecked(!mCardView.isChecked());
                return true;
            }
        });
    }
}
