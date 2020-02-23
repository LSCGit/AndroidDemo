package com.lsc.animation.transitions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lsc.animation.R;

public class TransitionActivity extends AppCompatActivity {

    private Button mBtnFirst;
    private Button mBtnSecond;

    private Fragment mFragmentFirst;
    private Fragment mFragmentSecond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        mBtnFirst = findViewById(R.id.first_btn);
        mBtnSecond = findViewById(R.id.second_btn);

        mFragmentFirst = new FirstFragment();
        mFragmentSecond = new SecondFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.transition_fl,mFragmentFirst);
        fragmentTransaction.commit();

        mBtnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //设置转场动画,必须放在Fragment改变操作之前，使用默认动画
                //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                //使用自定义的动画
                fragmentTransaction.setCustomAnimations(R.anim.in_anim1,R.anim.out_anim1,
                        R.anim.in_anim2,R.anim.out_anim2);
                fragmentTransaction.replace(R.id.transition_fl,mFragmentSecond);
                fragmentTransaction.addToBackStack("first");

                fragmentTransaction.commit();
            }
        });
    }


}
