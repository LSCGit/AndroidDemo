package com.lsc.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by lsc on 2020-02-22 13:37.
 * E-Mail:2965219926@qq.com
 *
 * View动画
 */
public class ViewAnimation {

    /**
     * 旋转中心在view的左上角
     *
     * @param view
     * @param <T>
     */
    public static <T extends View> void setRotateAnimation(T view){
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f,180f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        rotateAnimation.setRepeatCount(10);
        view.startAnimation(rotateAnimation);
    }

    /**
     * 以自身中心为中心点进行旋转
     * @param view
     * @param <T>
     */
    public static <T extends View> void setRotateSelfAnimation(T view){
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f,360f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        rotateAnimation.setRepeatCount(10);
        view.startAnimation(rotateAnimation);
    }

    /**
     *
     * 以自身中心为中心点进行旋转,不反向转
     * @param view
     * @param <T>
     */
    public static <T extends View> void setRotateRestartAnimation(T view){
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f,180f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(10);
        view.startAnimation(rotateAnimation);
    }

    public static <T extends View> void setRotateInterpolator(T view){
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f,180f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setRepeatCount(10);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        view.startAnimation(rotateAnimation);
    }


    /**
     * 移动动画
     * view的左上角为坐标原点
     *
     * @param view
     * @param <T>
     */
    public static <T extends View> void setTranslateAnimation(T view){

        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f,100f,
                0.0f,100f);
        translateAnimation.setRepeatCount(10);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setDuration(1000);
        view.startAnimation(translateAnimation);
    }

    /**
     * 缩放动画
     * 左上角为坐标原点
     * @param view
     * @param <T>
     */
    public static <T extends View> void setScaleAnimation(T view){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f,1.0f,0.5f,2.0f);
        scaleAnimation.setRepeatCount(10);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setDuration(1000);
        view.startAnimation(scaleAnimation);
    }

    /**
     * 透明度动画
     * @param view
     * @param <T>
     */
    public static <T extends View> void setAlphaAnimation(T view){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setRepeatCount(10);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);
    }

    /**
     *
     * 组合动画
     * @param view
     * @param <T>
     */
    public static <T extends View> void setAnimationSet(T view){
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f,360f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF, .5f);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(10);
        rotateAnimation.setInterpolator(new LinearInterpolator());

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f,1.5f,0.5f,1.5f);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setRepeatCount(10);

        //创建动画组合对象，参数表示是否所有动画共享同一个插值函数
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        view.startAnimation(animationSet);
    }

}
