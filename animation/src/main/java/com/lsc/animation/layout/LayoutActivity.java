package com.lsc.animation.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.lsc.animation.R;
import com.lsc.utils.LayoutAnimate;

/*
* Layout动画
* 排版动画*/
public class LayoutActivity extends AppCompatActivity {

    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        rl = findViewById(R.id.layout_rl);
        Button button = findViewById(R.id.layout_first);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton();
            }
        });
    }

    private void addButton(){
        Button button = new Button(this);
        button.setText("我是动态的");
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.addRule(RelativeLayout.BELOW,R.id.layout_first);
        button.setLayoutParams(layoutParams);
        LayoutAnimate.setLayoutAnimate(this,R.animator.property_animator,rl,button);
    }
}
