package com.lsc.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import com.lsc.ui.R;

/**
 * Created by lsc on 2020/5/9 06:35.
 * E-Mail:2965219926@qq.com
 */
public class SplashView extends View {

    //旋转圆的画笔
    private Paint mPaint;
    //扩散圆的画笔
    private Paint mHolePaint;
    //属性动画
    private ValueAnimator mValueAnimator;

    //背景色
    private int mBackgroundColor = Color.WHITE;
    //6个小圆颜色
    private int[] mCircleColors;

    //旋转圆的中心坐标
    private float mCenterX;
    private float mCenterY;

    //扩散圆的最大半径，为斜对角线的一半
    private float mDistance;

    //6个小圆的半径
    private float mCircleRadius = 18;
    //旋转大圆的半径
    private float mRotateRadius = 90;

    //当前大圆的旋转角度
    private float mCurrentRotateAngle = 0;
    //当前大圆的半径
    private float mCurrentRotateRadius = mRotateRadius;
    //扩散圆的半径
    private float mCurrentHoleRadius = 0;
    //旋转动画时长
    private int mRotateDuration = 1200;

    public SplashView(Context context) {
        this(context,null);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿

        mHolePaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mHolePaint.setStyle(Paint.Style.STROKE);
        mHolePaint.setColor(mBackgroundColor);

        mCircleColors = context.getResources().getIntArray(R.array.splash_circle_colors);

        rotateSixCircle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w * 1f /2;
        mCenterY = h * 1f /2;

        mDistance = (float) (Math.hypot(w,h) /2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mCurrentHoleRadius > 0){
            //绘制空心圆
            mHolePaint.setStrokeWidth(mDistance - mCurrentHoleRadius);
            //空心圆半径应为 strokeWidth /2 + mCurrentHoleRadius
            //strokeWidth的一半在圆中。 圆外1/2 | 圆内1/2
            canvas.drawCircle(mCenterX,mCenterY,mCurrentHoleRadius + (mDistance - mCurrentHoleRadius)/2,mHolePaint);
        }else {
            //设置背景色
            canvas.drawColor(mBackgroundColor);
            canvas.save();//保存canvas
            canvas.translate(mCenterX, mCenterY);//canvas平移到（mCenterX，mCenterY）
            canvas.rotate(mCurrentRotateAngle);//canvas旋转到 mCurrentRotateAngle
            //绘制6个小圆
            drawSixCircle(canvas);
            canvas.restore();//恢复canvas
        }

    }

    /**
     * 绘制6个小圆
     *
     * <p>保存canvas
     * 遍历 mCircleColors
     *   每画一个小圆后，canvas再旋转60度 360/6 =60
     * 最后将canvan恢复</p>
     * @param canvas
     */
    private void drawSixCircle(Canvas canvas){
        canvas.save();
        for (int i=0;i < mCircleColors.length;i++){
            mPaint.setColor(mCircleColors[i]);
            canvas.drawCircle(mCurrentRotateRadius,0,mCircleRadius,mPaint);
            canvas.rotate(60);
        }
        canvas.restore();
    }

    /**
     * 为小圆设置旋转动画
     * 并设置监听当动画播放完之后进行扩散动画
     *
     */
    private void rotateSixCircle(){
        mValueAnimator = ValueAnimator.ofFloat(0,360);
        mValueAnimator.setDuration(mRotateDuration);
        mValueAnimator.setRepeatCount(2);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentRotateAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                meginSixCircle();
            }
        });
        mValueAnimator.start();
    }

    /**
     * 为小圆设置扩散聚合动画
     */
    private void meginSixCircle(){
        mValueAnimator = ValueAnimator.ofFloat(0,mRotateRadius);
        mValueAnimator.setDuration(mRotateDuration);
        mValueAnimator.setInterpolator(new OvershootInterpolator(10f));
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentRotateRadius = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                expandHoleCircle();
            }
        });
        mValueAnimator.reverse();
    }

    /**
     * 水波纹动画
     */
    private void expandHoleCircle(){
        mValueAnimator = ValueAnimator.ofFloat(mCircleRadius,mDistance);
        mValueAnimator.setDuration(mRotateDuration);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentHoleRadius = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        mValueAnimator.start();
    }
}
