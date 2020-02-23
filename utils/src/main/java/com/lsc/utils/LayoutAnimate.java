package com.lsc.utils;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.AnimatorRes;

/**
 * Created by lsc on 2020-02-22 21:08.
 * E-Mail:2965219926@qq.com
 *
 * 排版动画，针对ViewGroup
 */
public class LayoutAnimate {

    /**
     *
     * 设置Layout动画，控件出现时
     * @param context
     * @param id
     * @param viewGroup
     * @param view
     */
    public static void setLayoutAnimate(Context context, @AnimatorRes int id, ViewGroup viewGroup, View view){
        LayoutTransition transition = new LayoutTransition();
        //当一个控件出现时，我希望他是大小变化的动画
        //利用AnimatorInflater从资源加载动画
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context,id);
        //设置控件出现时的动画
        transition.setAnimator(LayoutTransition.APPEARING,set);
        //设置控件出现时，其它控件位置改变动画的持续事件
        transition.setDuration(LayoutTransition.CHANGE_APPEARING,4000);
        viewGroup.setLayoutTransition(transition);
        viewGroup.addView(view);
    }
}
