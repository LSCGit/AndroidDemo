package com.lsc.material.bottomsheet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.lsc.material.R;

public class BottomSheetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheets);

        BottomSheetBehavior bottomSheets = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        //设置默认先隐藏
        //bottomSheets.setState(BottomSheetBehavior.STATE_HIDDEN);
        //显示
        bottomSheets.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheets.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //拖动
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //状态变化
            }
        });
    }

}
