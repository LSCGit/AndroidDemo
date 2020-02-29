package com.lsc.utils;

import android.content.Context;

/**
 * Created by lsc on 2020-02-28 19:59.
 * E-Mail:2965219926@qq.com
 *
 * 像素和dp转换
 */
public class DpPx {

    public static int dip2px(Context context,float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue){

        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
}
