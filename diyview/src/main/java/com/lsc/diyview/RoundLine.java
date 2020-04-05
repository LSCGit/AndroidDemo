package com.lsc.diyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by lsc on 2020-04-01 16:51.
 * E-Mail:2965219926@qq.com
 */
public class RoundLine extends View {

    private Paint mPaint;

    public RoundLine(Context context) {
        super(context);

    }

    public RoundLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){

        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#000000"));
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0,0,100,0,mPaint);
    }
}
