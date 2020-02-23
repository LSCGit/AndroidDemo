package com.lsc.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.lsc.utils.ReflectInvoke;

/**
 * Created by lsc on 2020-02-21 21:37.
 * E-Mail:2965219926@qq.com
 *
 * 方案2
 * HOOK
 * Instrumentation的包装类
 */
public class EvilInstrumentation extends Instrumentation {

    private static final String TAG = "EviiInstrumentation";

    //ActivityThread中原始的对象，保存起来
    Instrumentation mBase;

    public EvilInstrumentation(Instrumentation instrumentation){
        mBase = instrumentation;
    }

    public ActivityResult execStartActivity(Context context, IBinder contextThread,
                                            IBinder token, Activity target,
                                            Intent intent, int resquestCode,
                                            Bundle options){

        Log.d(TAG," l s c 到此一游 ！");
        //开始调用原始的方法，调不调随你，但是不调用的话，所有的startActivity都失效。
        //由于这个方法是隐藏的，因此需要使用反射调用，首先找到这个方法。
        Class[] paramsTypes = {Context.class, IBinder.class,IBinder.class,Activity.class,
                        Intent.class,int.class,Bundle.class};
        Object[] paramsValues = {context,contextThread,token,target,intent,resquestCode,options};

        return (ActivityResult) ReflectInvoke.invokeInstanceMethod(mBase,"execStartActivity",paramsTypes,paramsValues);
    }
}
