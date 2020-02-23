package com.lsc.animation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.lsc.animation.R;
import com.lsc.utils.ViewAnimation;

import java.lang.annotation.Annotation;

public class ViewAnimationActivity extends AppCompatActivity {

    private ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);

        mImg = findViewById(R.id.view_animation_img);
        ViewAnimation.setAlphaAnimation(mImg);
    }

}
