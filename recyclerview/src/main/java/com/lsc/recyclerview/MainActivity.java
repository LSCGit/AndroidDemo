package com.lsc.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lsc.recyclerview.fragment.ComplexFragment;
import com.lsc.recyclerview.fragment.EasyItemFragment;

/*
* RecyclerView只负责显示子控件，排列其子控件，滚动其子控件
* Adapter管理每个条目显示的内容，每个子控件是由Adapter创建的。
*
* */
public class MainActivity extends AppCompatActivity {

    private Button mBtnEasyItem;
    private Button mBtnComplex;
    private FragmentManager mManager;

    private EasyItemFragment mEasyItemFrag;
    private ComplexFragment mComplexFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager = getSupportFragmentManager();
        FragmentTransaction transaction = mManager.beginTransaction();
        mEasyItemFrag = new EasyItemFragment();
        transaction.add(R.id.framelayout,mEasyItemFrag);
        transaction.commit();

        initView();
    }

    private void initView(){
        mBtnEasyItem = findViewById(R.id.btn_one);
        mBtnComplex = findViewById(R.id.btn_two);

        mBtnEasyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mManager.beginTransaction().replace(R.id.framelayout,mEasyItemFrag).commit();
            }
        });

        mBtnComplex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mComplexFragment == null){
                    mComplexFragment = new ComplexFragment();
                }
                mManager.beginTransaction().replace(R.id.framelayout,mComplexFragment).commit();

            }
        });

    }
}
