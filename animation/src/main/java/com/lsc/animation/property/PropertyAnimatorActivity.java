package com.lsc.animation.property;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.lsc.animation.R;
import com.lsc.utils.PropertyAnimator;

public class PropertyAnimatorActivity extends AppCompatActivity {

    private ImageView mImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animator);

        mImg = findViewById(R.id.property_animator_img);

        PropertyAnimator.setPropertyAnimatorByXml(mImg,this,R.animator.property_animator);
    }
}
