package com.lsc.designpattern.pattern.singleton;

/**
 * Created by lsc on 2020-04-18 10:37.
 * E-Mail:2965219926@qq.com
 *
 *饿汉式 安全的
 */
//单例模式通用代码
public class Singleton {

    private static final Singleton singleton = new Singleton();
    //限制产生多个对象
    private Singleton(){}
    //通过该方法获得实例对象
    public static Singleton getSingleton(){
        return singleton;
    }
    //类中其它方法，尽量static
    public static void doSomething(){}
}
