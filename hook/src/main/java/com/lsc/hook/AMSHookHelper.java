package com.lsc.hook;

import com.lsc.utils.ReflectInvoke;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * Created by lsc on 2020-02-21 22:09.
 * E-Mail:2965219926@qq.com
 *
 * 方案3
 *
 */
public class AMSHookHelper {

    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    public static void hookAMN()throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException,
            IllegalAccessException,NoSuchFieldException{

        //获取AMN的gDefault单例gDefault，gDefault是final静态的。
        Object gDefault = ReflectInvoke.getStaticFieldObject("android.app.ActivityManagerNative",
                "gDefault");
        //gDefault是一个Android.util.Singleton<T>对象；去除这个单例中的mInstance字段
        Object mInstance = ReflectInvoke.getFieldObject("android.util.Singleton",
                gDefault,"mInstance");
        //创建一个这个对象的代理对象MockClass1，然后替换这个字段，让我门的代理对象帮忙干活
        Class<?> classB2Interface = Class.forName("android.app.IActivityManager");
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{classB2Interface},
                new MockClass(mInstance));

        //把gDefault的mInstance字段，修改为proxy
        ReflectInvoke.setFieldObject("android.util.Singleton",gDefault,
                "mInstance",proxy);
    }

}
