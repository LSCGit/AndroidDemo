package com.lsc.hook;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by lsc on 2020-02-22 07:39.
 * E-Mail:2965219926@qq.com
 */
public class MockClass implements InvocationHandler {
    private static final String TAG = "MockClass";

    Object mBase;

    public MockClass(Object object){
        mBase = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if ("startActivity".equals(method.getName())){
            Log.e("lsc",method.getName());
        }
        return method.invoke(mBase,args);
    }


}