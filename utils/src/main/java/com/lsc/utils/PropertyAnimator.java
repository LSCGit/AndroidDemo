package com.lsc.utils;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.AnimatorRes;

/**
 * Created by lsc on 2020-02-22 17:21.
 * E-Mail:2965219926@qq.com
 *
 * 属性动画
 */
public class PropertyAnimator {

    private PropertyAnimator(){}
    private static class SingleHolder{
        private static final PropertyAnimator instance = new PropertyAnimator();
    }

    public static PropertyAnimator getInstance(){
        return SingleHolder.instance;
    }

    /**
     * 以自身中心进行旋转。
     * @param view
     * @param <T>
     */
    public static <T extends View> void setRotateAnimator(T view){

        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(view,"rotation",
                .0f,180.0f);
        rotateAnimator.setDuration(1000);
        rotateAnimator.setRepeatCount(10);
        rotateAnimator.setRepeatMode(ValueAnimator.REVERSE);
        rotateAnimator.setInterpolator(new LinearInterpolator());

        rotateAnimator.start();
    }

    /**
     * 属性动画组合
     *
     * @param view
     * @param <T>
     */
    public static <T extends View> void setPropertySet(T view){
        //创建一个旋转动画
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(view,"rotation",.0f,180.0f);
        rotateAnimator.setDuration(1000);
        rotateAnimator.setRepeatMode(ValueAnimator.REVERSE);
        rotateAnimator.setRepeatCount(2);
        rotateAnimator.setInterpolator(new LinearInterpolator());

        //创建一个缩放动画，X轴
        ObjectAnimator scaleAnimatorX = ObjectAnimator.ofFloat(view,"scaleX",
                0.5f,1.5f);
        scaleAnimatorX.setDuration(1000);
        scaleAnimatorX.setRepeatCount(10);
        scaleAnimatorX.setRepeatMode(ValueAnimator.REVERSE);

        //创建一个缩放动画，Y轴
        ObjectAnimator scaleAnimatorY = ObjectAnimator.ofFloat(view,"scaleY",0.5f,1.5f);
        scaleAnimatorY.setDuration(1000);
        scaleAnimatorY.setRepeatCount(10);
        scaleAnimatorY.setRepeatMode(ValueAnimator.REVERSE);

        //创建一个动画组
        //先旋转，再同时缩放XY
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimatorX).with(scaleAnimatorY).
                after(rotateAnimator);
        animatorSet.start();
    }

    /**
     * 资源文件 加载属性动画
     * @param view
     * @param context
     * @param id
     * @param <T>
     */
    public static <T extends View> void setPropertyAnimatorByXml(T view,Context context, @AnimatorRes int id){
        //利用AnimatorInflater从资源加载动画
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, id);
        animatorSet.setTarget(view);
        animatorSet.start();
    }
}
