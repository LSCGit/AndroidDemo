package com.lsc.qq.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by lsc on 2020-02-28 16:16.
 * E-Mail:2965219926@qq.com
 *
 * 禁止左右滑动，将事件消费
 */
public class QQViewPager extends ViewPager {

    public QQViewPager(@NonNull Context context) {
        super(context);
    }

    public QQViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
