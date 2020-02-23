package com.lsc.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lsc.animation.layout.LayoutActivity;
import com.lsc.animation.property.PropertyAnimatorActivity;
import com.lsc.animation.transitions.TransitionActivity;
import com.lsc.animation.view.ViewAnimationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Button viewButton = new Button(this);
        viewButton.setText("View动画");
        viewButton.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        Button propertyButton = new Button(this);
        propertyButton.setText("属性动画");
        propertyButton.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        Button layoutButton = new Button(this);
        layoutButton.setText("Layout动画");
        layoutButton.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        Button transitionButton = new Button(this);
        transitionButton.setText("转场动画");
        transitionButton.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.addView(viewButton);
        linearLayout.addView(propertyButton);
        linearLayout.addView(layoutButton);
        linearLayout.addView(transitionButton);
        setContentView(linearLayout,linearLayoutParams);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewAnimationActivity.class);
                startActivity(intent);
            }
        });

        propertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PropertyAnimatorActivity.class);
                startActivity(intent);
            }
        });

        layoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LayoutActivity.class);
                startActivity(intent);
            }
        });

        transitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransitionActivity.class);
                startActivity(intent);
            }
        });
    }
}
